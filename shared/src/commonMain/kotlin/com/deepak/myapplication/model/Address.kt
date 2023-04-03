package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Address(
    val line: List<String>?,
    val city: String?,
    val state: String?,
    val postalCode: String?,
    val country: String?
)
