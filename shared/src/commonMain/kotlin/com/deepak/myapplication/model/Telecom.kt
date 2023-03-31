package com.deepak.myapplication.model


@kotlinx.serialization.Serializable
data class Telecom(val system: String?, val value: String?, val use: String?, val extension: List<Extension>?)
