package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class LocationDataResp(
    val resourceType: String,
    val id: String,
    val total: Int,
    val entry: List<LocationEntry>?
)