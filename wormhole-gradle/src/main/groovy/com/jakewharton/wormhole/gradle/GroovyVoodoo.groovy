package com.jakewharton.wormhole.gradle

import com.android.build.gradle.BaseExtension
import org.gradle.api.tasks.StopExecutionException

import java.util.function.Function

class GroovyVoodoo {
  private Function<Integer, String> integerHandler
  private Function<String, String> stringHandler

  GroovyVoodoo(
      Function<Integer, String> integerHandler,
      Function<String, String> stringHandler
  ) {
    this.integerHandler = integerHandler
    this.stringHandler = stringHandler
  }

  void applyTo(BaseExtension extension) {
    extension.metaClass.wormhole {
      if (it instanceof Integer) {
        return integerHandler.apply(it)
      } else if (it instanceof String) {
        return stringHandler.apply(it)
      } else {
        throw new StopExecutionException("Compilation platform must be specified as integer or string")
      }
    }
  }
}
