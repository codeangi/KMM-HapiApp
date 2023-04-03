package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Parameter(
    val name: String,
    val valueDateTime: String? = null,
    val valueReference: ValueReference? = null
)

@kotlinx.serialization.Serializable
data class ValueReference(val reference: String)