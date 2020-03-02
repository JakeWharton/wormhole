package com.jakewharton.wormhole.lint

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiTest {
  @Test fun roundTrip() {
    val xml = """
      |<?xml version="1.0" encoding="utf-8"?>
      |<api version="2">
      |  <class name="com/example/Empty" since="1"/>
      |  <class name="com/example/Deprecated" since="1" deprecated="2"/>
      |  <class name="com/example/Removed" since="1" removed="3"/>
      |  <class name="com/example/Base" since="1">
      |    <extends name="com/example/Empty"/>
      |  </class>
      |  <class name="com/example/Example" since="1">
      |    <extends name="com/example/Base" since="2"/>
      |    <extends name="com/example/Empty" removed="2"/>
      |    <implements name="com/example/Interface1"/>
      |    <implements name="com/example/Interface2" since="2"/>
      |    <implements name="com/example/Interface3" removed="3"/>
      |    <method name="&lt;init>()V"/>
      |    <method name="method1()V"/>
      |    <method name="method2()V" since="2"/>
      |    <method name="method3()V" deprecated="2"/>
      |    <method name="method4()V" removed="2"/>
      |    <field name="FIELD_1"/>
      |    <field name="FIELD_2" since="2"/>
      |    <field name="FIELD_3" deprecated="2"/>
      |    <field name="FIELD_4" removed="2"/>
      |  </class>
      |</api>
    """.trimMargin()

    val expected = LintApi(2, listOf(
        LintClass("com/example/Empty", since = 1),
        LintClass("com/example/Deprecated", since = 1, deprecated = 2),
        LintClass("com/example/Removed", since = 1, removed = 3),
        LintClass("com/example/Base", since = 1,
            extends = listOf(
                LintExtends("com/example/Empty")
            )
        ),
        LintClass("com/example/Example", since = 1,
            extends = listOf(
                LintExtends("com/example/Base", since = 2),
                LintExtends("com/example/Empty", removed = 2)
            ),
            implements = listOf(
                LintImplements("com/example/Interface1"),
                LintImplements("com/example/Interface2", since = 2),
                LintImplements("com/example/Interface3", removed = 3)
            ),
            methods = listOf(
                LintMethod("<init>()V"),
                LintMethod("method1()V"),
                LintMethod("method2()V", since = 2),
                LintMethod("method3()V", deprecated = 2),
                LintMethod("method4()V", removed = 2)
            ),
            fields = listOf(
                LintField("FIELD_1"),
                LintField("FIELD_2", since = 2),
                LintField("FIELD_3", deprecated = 2),
                LintField("FIELD_4", removed = 2)
            )
        )
    ))

    val actual = LintApi.deserialize(xml)
    assertThat(actual).isEqualTo(expected)

    assertThat(actual.serialize()).isEqualTo("""
      |<api version="2">
      |  <class name="com/example/Empty" since="1"></class>
      |  <class name="com/example/Deprecated" since="1" deprecated="2"></class>
      |  <class name="com/example/Removed" since="1" removed="3"></class>
      |  <class name="com/example/Base" since="1">
      |    <extends name="com/example/Empty"></extends>
      |  </class>
      |  <class name="com/example/Example" since="1">
      |    <extends name="com/example/Base" since="2"></extends>
      |    <extends name="com/example/Empty" removed="2"></extends>
      |    <implements name="com/example/Interface1"></implements>
      |    <implements name="com/example/Interface2" since="2"></implements>
      |    <implements name="com/example/Interface3" removed="3"></implements>
      |    <method name="&lt;init&gt;()V"></method>
      |    <method name="method1()V"></method>
      |    <method name="method2()V" since="2"></method>
      |    <method name="method3()V" deprecated="2"></method>
      |    <method name="method4()V" removed="2"></method>
      |    <field name="FIELD_1"></field>
      |    <field name="FIELD_2" since="2"></field>
      |    <field name="FIELD_3" deprecated="2"></field>
      |    <field name="FIELD_4" removed="2"></field>
      |  </class>
      |</api>
    """.trimMargin())
  }
}
