package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class CareTeamResp(
    val resourceType: String,
    val total: Int?,
    val entry: List<Entry>?
)


