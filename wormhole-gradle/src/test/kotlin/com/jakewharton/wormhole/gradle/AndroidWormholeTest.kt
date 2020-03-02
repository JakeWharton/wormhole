package com.jakewharton.wormhole.gradle

import com.google.common.truth.Truth.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test
import java.io.File
import java.util.Properties

class AndroidWormholeTest {
  private val fixturesDir = File("src/test/fixture")

  @Test fun library() {
    val fixtureDir = File(fixturesDir, "library-groovy")
    File(fixtureDir, "local.properties").writeText("sdk.dir=$androidHome\n")
    val gradleRoot = File(fixtureDir, "gradle").apply {
      mkdir()
    }
    File("../gradle/wrapper").copyRecursively(File(gradleRoot, "wrapper"), true)

    val result = GradleRunner.create()
        .withProjectDir(fixtureDir)
        .withPluginClasspath()
        .withArguments("clean", "assembleDebug", "lintDebug", "--stacktrace")
        .build()
    assertThat(result.output).contains("BUILD SUCCESSFUL")
  }

  @Test fun libraryKts() {
    val fixtureDir = File(fixturesDir, "library-kts")
    File(fixtureDir, "local.properties").writeText("sdk.dir=$androidHome\n")
    val gradleRoot = File(fixtureDir, "gradle").apply {
      mkdir()
    }
    File("../gradle/wrapper").copyRecursively(File(gradleRoot, "wrapper"), true)

    val result = GradleRunner.create()
        .withProjectDir(fixtureDir)
        .withPluginClasspath()
        .withArguments("clean", "assembleDebug", "lintDebug", "--stacktrace")
        .build()
    assertThat(result.output).contains("BUILD SUCCESSFUL")
  }

  @Test fun libraryInvalidType() {
    val fixtureDir = File(fixturesDir, "library-invalid-type")
    File(fixtureDir, "local.properties").writeText("sdk.dir=$androidHome\n")
    val gradleRoot = File(fixtureDir, "gradle").apply {
      mkdir()
    }
    File("../gradle/wrapper").copyRecursively(File(gradleRoot, "wrapper"), true)

    val result = GradleRunner.create()
        .withProjectDir(fixtureDir)
        .withPluginClasspath()
        .withArguments("clean", "--stacktrace")
        .buildAndFail()
    assertThat(result.output).apply {
      contains("BUILD FAILED")
      contains("Compilation platform must be specified as integer or string")
    }
  }

  private val androidHome: String get() {
    val env = System.getenv("ANDROID_HOME")
    if (env != null) {
      return env.withInvariantPathSeparators()
    }
    val localProp = File(File(System.getProperty("user.dir")).parentFile, "local.properties")
    if (localProp.exists()) {
      val prop = Properties()
      localProp.inputStream().use {
        prop.load(it)
      }
      val sdkHome = prop.getProperty("sdk.dir")
      if (sdkHome != null) {
        return sdkHome.withInvariantPathSeparators()
      }
    }
    throw IllegalStateException(
        "Missing 'ANDROID_HOME' environment variable or local.properties with 'sdk.dir'")
  }

  private fun String.withInvariantPathSeparators() = replace("\\", "/")
}
