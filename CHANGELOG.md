Change Log
==========

Version 0.2.0 *(2020-02-27)*
----------------------------

 * New: Migrate Groovy-based usage to a Gradle plugin which changes the means of applying.

   ```diff
   -import com.jakewharton.wormhole.gradle.AndroidWormhole
   +apply plugin: 'com.jakewharton.wormhole'

    android {
   -  compileSdkVersion AndroidWormhole.create(29)
   +  compileSdkVersion wormhole(29)
    }
   ```


Version 0.1.0 *(2020-02-27)*
----------------------------

Initial release.
