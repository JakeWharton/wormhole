package com.jakewharton.wormhole.jar

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.Opcodes.ACC_STATIC
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class AndroidJarRewriter
@JvmOverloads constructor(
  val debug: Boolean = false
) {
  fun rewrite(androidJar: Path, desugarSignatures: List<String>, wormholeJar: Path) {
    require(Files.isRegularFile(androidJar)) { "$androidJar is not a file" }
    require(Files.notExists(wormholeJar)) { "$wormholeJar already exists" }

    val desugarSignatureMap = desugarSignatures.map(::parseDesugarSignature)
        .groupBy(MethodHolder::owner, MethodHolder::method)
        .mapKeys { (it, _) -> "${it.replace('.', '/')}.class" }

    val wormholeUri = URI("jar:file", wormholeJar.toUri().path, null)
    FileSystems.newFileSystem(wormholeUri, mapOf("create" to "true"), null).use { wormholeJarFs ->
      val wormholeJarRoot = wormholeJarFs.rootDirectories.single()
      FileSystems.newFileSystem(androidJar, null as ClassLoader?).use { androidJarFs ->
        val androidJarRoot = androidJarFs.rootDirectories.single()
        travelThroughWormhole(androidJarRoot, desugarSignatureMap, wormholeJarRoot)
      }
    }
  }

  private fun parseDesugarSignature(desugarSignature: String): MethodHolder {
    // For example: java/util/Objects#checkFromToIndex(III)I
    val hash = desugarSignature.indexOf('#')
    val owner = desugarSignature.substring(0, hash)
    val leftParen = desugarSignature.indexOf('(')
    val name = desugarSignature.substring(hash + 1, leftParen)
    val descriptor = desugarSignature.substring(leftParen)
    val method = Method(name = name, descriptor = descriptor)
    val sweetenedMethod = methodSweetener[desugarSignature]?.invoke(method) ?: method
    return MethodHolder(owner, sweetenedMethod)
  }

  private fun travelThroughWormhole(
    androidJarRoot: Path,
    desugarMap: Map<String, List<Method>>,
    wormholeJarRoot: Path
  ) {
    Files.walkFileTree(androidJarRoot, object : SimpleFileVisitor<Path>() {
      override fun visitFile(
        androidJarPath: Path,
        attrs: BasicFileAttributes
      ): FileVisitResult {
        val relativePathString = androidJarRoot.relativize(androidJarPath).toString()
        val wormholeJarPath = wormholeJarRoot.resolve(relativePathString)
        Files.createDirectories(wormholeJarPath.parent)

        val methodSignatures = desugarMap[relativePathString]
        if (methodSignatures != null) {
          if (debug) println("Processing $relativePathString...")
          val androidClassBytes = Files.readAllBytes(androidJarPath)
          val wormholeClassBytes = travelThroughWormhole(androidClassBytes, methodSignatures)
          Files.write(wormholeJarPath, wormholeClassBytes)
        } else {
          Files.copy(androidJarPath, wormholeJarPath)
        }

        return CONTINUE
      }
    })
  }

  private fun travelThroughWormhole(
    androidClassBytes: ByteArray,
    desugarMethods: List<Method>
  ) : ByteArray {
    val writer = ClassWriter(null, 0)
    val signatureRecorder = SignatureRecordingVisitor(writer, debug)
    ClassReader(androidClassBytes).accept(signatureRecorder, 0)

    val missingSignatures = desugarMethods - signatureRecorder.seenMethods
    for (missingSignature in missingSignatures) {
      if (debug) println("Synthesizing $missingSignature")
      writer.visitMethod(
          missingSignature.access,
          missingSignature.name,
          missingSignature.descriptor,
          missingSignature.signature,
          missingSignature.exceptions.toTypedArray()
      ).apply {
        visitCode()
        visitMaxs(0, 0)
        visitEnd()
      }
    }

    return writer.toByteArray()
  }
}

private class SignatureRecordingVisitor(
  delegate: ClassWriter,
  private val debug: Boolean
) : ClassVisitor(Opcodes.ASM7, delegate) {
  private val _seenMethods = mutableListOf<Method>()
  val seenMethods: List<Method> get() = _seenMethods

  override fun visitMethod(access: Int, name: String, descriptor: String, signature: String?,
    exceptions: Array<out String>?): MethodVisitor {
    if (debug) {
      println("visitMethod[access: $access, name: $name, descriptor: $descriptor, signature: $signature, exceptions: ${exceptions?.contentToString()}]")
    }
    _seenMethods += Method(access, name, descriptor, signature)
    return super.visitMethod(access, name, descriptor, signature, exceptions)
  }
}

private data class MethodHolder(
  val owner: String,
  val method: Method
)

data class Method(
  val access: Int = ACC_PUBLIC or ACC_STATIC,
  val name: String,
  val descriptor: String,
  val signature: String? = null,
  val exceptions: List<String> = emptyList()
)
