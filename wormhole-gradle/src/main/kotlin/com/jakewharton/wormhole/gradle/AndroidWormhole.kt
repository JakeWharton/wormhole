@file:JvmName("AndroidWormhole")

package com.jakewharton.wormhole.gradle

import com.android.build.gradle.BaseExtension
import com.android.tools.r8.BackportedMethodList
import com.android.tools.r8.BackportedMethodListCommand
import com.jakewharton.wormhole.jar.AndroidJarRewriter
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import com.android.tools.r8.Version as R8Version

fun BaseExtension.wormhole(api: Int) = createWormhole(api)
fun BaseExtension.wormhole(platform: String) = createWormhole(platform)

@JvmName("create")
fun createWormhole(api: Int): String {
  return createWormhole("android-$api")
}

@JvmName("create")
fun createWormhole(platform: String): String {
  val r8Version = R8Version.getVersionString().substringBefore(' ')
  val wormholeCodename = "wormhole-$wormholeVersion-$platform-r8-$r8Version"
  val wormholePlatform = "android-$wormholeCodename"

  val sdkDir = Paths.get(System.getenv("ANDROID_HOME")) // TODO Find AGP canonical internal API.

  val platformsDir = sdkDir.resolve("platforms")
  val wormholeDir = platformsDir.resolve(wormholePlatform)
  val wormholeAndroidJarFile = wormholeDir.resolve("android.jar")
  if (Files.notExists(wormholeAndroidJarFile)) {
    val platformDir = platformsDir.resolve(platform)
    val androidJarFile = platformDir.resolve("android.jar")
    if (Files.notExists(androidJarFile)) {
      // TODO try to install automatically
      throw IllegalArgumentException("Platform '$platform' not installed")
    }

    // Copy everything over except for the android.jar to look like the original platform.
    Files.createDirectory(wormholeDir)
    Files.walkFileTree(platformDir, object : SimpleFileVisitor<Path>() {
      override fun visitFile(source: Path, attrs: BasicFileAttributes): FileVisitResult {
        val relativePath = platformDir.relativize(source).toString()
        val destination = wormholeDir.resolve(relativePath)
        Files.createDirectories(destination.parent)

        if (relativePath == "source.properties") {
          val properties = Files.readAllLines(source, UTF_8)
              .filterNot { it.startsWith(codenameProperty) }
              .plus(codenameProperty + wormholeCodename)
              .joinToString("\n")
          Files.write(destination, properties.toByteArray(UTF_8))
        } else if (relativePath != "android.jar" && relativePath != "package.xml") {
          Files.copy(source, destination)
        }
        return CONTINUE
      }
    })

    val desugaredApiSignatures = desugaredApiSignatures()
    AndroidJarRewriter().rewrite(androidJarFile, desugaredApiSignatures, wormholeAndroidJarFile)
  }
  return wormholePlatform
}

private const val codenameProperty = "AndroidVersion.CodeName="

internal fun desugaredApiSignatures(): List<String> {
  val signatures = mutableListOf<String>()
  val command = BackportedMethodListCommand.builder()
      .setMinApiLevel(1) // Just get them all, we'll ignore existing APIs anyway.
      .setConsumer { signature, _ -> signatures += signature }
      .build()
  BackportedMethodList.run(command)
  return signatures
}
