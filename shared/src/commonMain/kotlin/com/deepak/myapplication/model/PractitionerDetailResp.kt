package com.deepak.myapplication.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PractitionerDetailResp(
    val resourceType: String,
    val id: String,
    val language: String?,
    @SerialName("active")
    val isActive: Boolean?,
    val extension: List<Extension>?,
    val name:List<Name>?,
    val telecom: List<Telecom>?,
    val address: List<Address>?,
    val gender:String?,
    val qualification: List<Qualification>?,
    val communication: List<Coding>?
)
