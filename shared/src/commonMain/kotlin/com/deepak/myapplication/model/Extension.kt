package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Extension(
    val url: String?,
    val valueInteger: Int?,
    val valueString: String?,
    val valueBoolean: Boolean?
)