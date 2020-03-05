Wormhole
========

A time-traveling bytecode rewriter which adds future APIs to `android.jar` which can be desugared
to all API levels by D8 and R8.

In your root `build.gradle`:
```groovy
buildscript {
  dependencies {
    classpath 'com.jakewharton.wormhole:wormhole-gradle:0.3.1'
  }
  repositories {
    mavenCentral()
  }
}
```

In any Android subproject's `build.gradle`:
```groovy
apply plugin: 'com.jakewharton.wormhole'

android {
  compileSdkVersion wormhole(29)
}
```
or `build.gradle.kts`:
```kotlin
import com.jakewharton.wormhole.gradle.wormhole

android {
  compileSdkVersion(wormhole(29))
}
```

In your code you should now be able to call methods which don't (yet) exist in the `android.jar`:
```java
// Available in API 30.
List<String> names = List.of("Jake");
// Java 10 API not available in any API level yet.
List<String> copy = List.copyOf(names);
```


FAQ
---

#### Why?

Adding new Java APIs to `android.jar` is harder than it should be and slower than it should be.
It's easy, in contrast, to add desugarings of future Java APIs to D8/R8.

In Android R there are new methods from Java 9 such as `List.of`. Thanks to D8 and R8, these are not
exclusive to API 30, but instead instantly work down to API 1.

There are a set of desugarings in D8 and R8 for APIs which are not _yet_ in `android.jar`. Instead
of having to wait to use them, this project makes them available immediately.

#### Should I use this in an app?

Sure.

#### Should I use this in a library?

Is the library in the same repo as your app? It's safe to use.

Is the library standalone and shipped as a binary? It is not safe to use.
You can't know what version of AGP/R8 your consumers are using to ensure new methods will be desugared.

#### Does it work with all versions of AGP(Android Gradle Plugin)?

No. It works with AGP v4.0 and above.

License
=======

    Copyright 2020 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
