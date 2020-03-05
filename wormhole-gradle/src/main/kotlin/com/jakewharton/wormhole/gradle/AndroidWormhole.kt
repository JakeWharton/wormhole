package com.jakewharton.wormhole.gradle

import com.android.Version.ANDROID_GRADLE_PLUGIN_VERSION
import com.android.build.gradle.BaseExtension
import com.android.tools.r8.BackportedMethodList
import com.android.tools.r8.BackportedMethodListCommand
import com.jakewharton.wormhole.jar.AndroidJarRewriter
import com.jakewharton.wormhole.lint.LintApi
import com.jakewharton.wormhole.lint.LintMethod
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.util.VersionNumber
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.Function
import com.android.tools.r8.Version as R8Version

fun BaseExtension.wormhole(api: Int) = AndroidWormhole.createWormhole(api)
fun BaseExtension.wormhole(platform: String) = AndroidWormhole.createWormhole(platform)

class AndroidWormhole : Plugin<Project> {
  override fun apply(project: Project) {
    val pluginType = try {
      BasePlugin::class.java
    } catch (e: ClassNotFoundException) {
      throw RuntimeException("Android plugin must be applied before Wormhole", e)
    }
    project.plugins.withType(pluginType) {
      val voodoo = GroovyVoodoo(Function(::createWormhole), Function(::createWormhole))
      val extension = project.extensions.getByType(BaseExtension::class.java)
      voodoo.applyTo(extension)
    }
  }

  companion object {
    @JvmStatic
    @JvmName("create")
    fun createWormhole(api: Int): String {
      return createWormhole("android-$api")
    }

    @JvmStatic
    @JvmName("create")
    fun createWormhole(platform: String): String {
      check(VersionNumber.parse(ANDROID_GRADLE_PLUGIN_VERSION).major >= 4) {
        "Android Gradle plugin 4.0.0 or newer is required"
      }

      val r8Version = R8Version.getVersionString().substringBefore(' ')
      val wormholeCodename = "wormhole-$wormholeVersion-$platform-r8-$r8Version"
      val wormholePlatform = "android-$wormholeCodename"

      val sdkDir = Paths.get(System.getenv("ANDROID_HOME")) // TODO Find AGP canonical internal API.

      val platformsDir = sdkDir.resolve("platforms")
      val wormholeDir = platformsDir.resolve(wormholePlatform)
      if (Files.notExists(wormholeDir)) {
        val platformDir = platformsDir.resolve(platform)
        if (Files.notExists(platformDir)) {
          // TODO try to install automatically
          throw IllegalArgumentException("Platform '$platform' not installed")
        }

        val desugaredApiSignatures = desugaredApiSignatures()

        Files.createDirectory(wormholeDir)
        Files.walkFileTree(platformDir, object : SimpleFileVisitor<Path>() {
          override fun visitFile(source: Path, attrs: BasicFileAttributes): FileVisitResult {
            val relativePath = platformDir.relativize(source).toString()
            val destination = wormholeDir.resolve(relativePath)
            Files.createDirectories(destination.parent)

            when (relativePath) {
              "source.properties" -> processSourceProperties(source, destination)
              "android.jar" -> processAndroidJar(source, destination)
              "data/api-versions.xml" -> processApiVersions(source, destination)
              "package.xml" -> {} // Do not copy. This is for platforms installed via SDK manager.
              else -> Files.copy(source, destination)
            }
            return CONTINUE
          }

          private fun processApiVersions(source: Path, destination: Path) {
            val sourceXml = Files.readAllBytes(source).toString(UTF_8)
            val sourceApi = LintApi.deserialize(sourceXml)

            val destinationClasses = sourceApi.classes.associateByTo(LinkedHashMap()) { it.name }
            for (desugaredApiSignature in desugaredApiSignatures) {
              val hash = desugaredApiSignature.indexOf('#')
              val type = desugaredApiSignature.substring(0, hash)
              val descriptor = desugaredApiSignature.substring(hash + 1)

              val lintClass = destinationClasses[type] ?: continue // TODO warn?
              val lintMethods = lintClass.methods.filter { it.descriptor != descriptor }
                  .plus(LintMethod(descriptor, since = backportApiLevel))

              destinationClasses[type] = lintClass.copy(methods = lintMethods)
            }

            val destinationApi = sourceApi.copy(classes = destinationClasses.values.toList())
            Files.write(destination, destinationApi.serialize().toByteArray(UTF_8))
          }

          fun processAndroidJar(source: Path, destination: Path) {
            AndroidJarRewriter().rewrite(source, desugaredApiSignatures, destination)
          }

          private fun processSourceProperties(source: Path, destination: Path) {
            val properties = Files.readAllLines(source, UTF_8)
                .filterNot { it.startsWith(codenameProperty) }
                .plus(codenameProperty + wormholeCodename)
                .joinToString("\n")
            Files.write(destination, properties.toByteArray(UTF_8))
          }
        })
      }
      return wormholePlatform
    }
  }
}

/** Assume R8 can backport methods to API 14 (since this is the lowest VM backport tests run on). */
private const val backportApiLevel = 14
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
