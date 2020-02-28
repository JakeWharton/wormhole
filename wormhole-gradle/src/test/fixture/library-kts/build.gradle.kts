import com.jakewharton.wormhole.gradle.wormhole

plugins {
  id("com.android.library")
}

repositories {
  google {
    metadataSources {
      artifact()
    }
  }
}

android {
  compileSdkVersion(wormhole(29))

  defaultConfig {
    minSdkVersion(14)
  }
}
