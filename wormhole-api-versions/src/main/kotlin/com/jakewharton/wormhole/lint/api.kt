package com.jakewharton.wormhole.lint

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XML

@Serializable
@SerialName("api")
data class LintApi(
  val version: Int,
  @SerialName("class")
  val classes: List<LintClass>
) {
  fun serialize() = xml.stringify(LintApi.serializer(), this)

  companion object {
    private val xml = XML {
      indent = 2
    }

    @JvmStatic
    fun deserialize(string: String) = xml.parse(LintApi.serializer(), string)
  }
}

@Serializable
@SerialName("class")
data class LintClass(
  val name: String,
  val since: Int,
  val deprecated: Int? = null,
  val removed: Int? = null,
  @SerialName("extends")
  val extends: List<LintExtends> = emptyList(),
  @SerialName("implements")
  val implements: List<LintImplements> = emptyList(),
  @SerialName("method")
  val methods: List<LintMethod> = emptyList(),
  @SerialName("field")
  val fields: List<LintField> = emptyList()
)

@Serializable
@SerialName("extends")
data class LintExtends(
  val name: String,
  val since: Int? = null,
  val removed: Int? = null
)

@Serializable
@SerialName("implements")
data class LintImplements(
  val name: String,
  val since: Int? = null,
  val removed: Int? = null
)

@Serializable
@SerialName("method")
data class LintMethod(
  @SerialName("name")
  val descriptor: String,
  val since: Int? = null,
  val deprecated: Int? = null,
  val removed: Int? = null
)

@Serializable
@SerialName("field")
data class LintField(
  val name: String,
  val since: Int? = null,
  val deprecated: Int? = null,
  val removed: Int? = null
)
