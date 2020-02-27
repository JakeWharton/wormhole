package com.jakewharton.wormhole.gradle

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AndroidWormholeTest {
  @Test fun desugaredApiListIsNotEmpty() {
    println(desugaredApiSignatures().joinToString("\n"))
    assertThat(desugaredApiSignatures()).apply {
      isNotEmpty()
      contains("java/util/Objects#requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;")
    }
  }
}
