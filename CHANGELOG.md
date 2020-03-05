Change Log
==========

Version 0.3.1 *(2020-03-05)*
----------------------------

 * Fix: Query the Android SDK directory from the Android Gradle plugin.
 * Fix: Fail if the version of the Android Gradle plugin is too old.


Version 0.3.0 *(2020-03-02)*
----------------------------

 * New: Rewrite Lint's API database to be aware of desugared methods.

   Before this change, calling a method which D8/R8 would desugar would result in a NewApi error:
   ```java
   // With minSdkVersion 14:
   int result = Long.compare(1L, 2L); // Added in API 19
   ```
   ```
   > Task :lintDebug FAILED
   Example.java:5: Error: Call requires API level 19 (current min is 14): java.lang.Long#compare [NewApi]
       int result = Long.compare(1L, 2L);
                         ~~~~~~~
   ```

   After:
   ```
   > Task :lintDebug
   No issues found.
   ```

 * Fix: Restore plugin Java compatibility to JDK 8. 


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
