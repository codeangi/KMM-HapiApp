package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class CareTeamResp(
    val resourceType: String,
    val total: Int,
    val entry: List<CareTeamEntry>?
)

@kotlinx.serialization.Serializable
data class CareTeamEntry(val fullUrl:String, val resource: Resource)
