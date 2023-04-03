package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Code(
    val text: String?,
    val coding: List<Coding>?
)

@kotlinx.serialization.Serializable
data class Qualification(val code: Code)