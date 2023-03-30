package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Resource(
    val resourceType: String,
    val id: String,
    val category: List<Category>?,
    val subject: Subject?,
    val encounter: Encounter?,
    val participant: List<Participant>?,
    val reasonCode: List<ReasonCode>?
)
@kotlinx.serialization.Serializable
data class Category(val coding:List<Coding>?)
@kotlinx.serialization.Serializable
data class Subject(val reference: String?)
@kotlinx.serialization.Serializable
data class Encounter(val reference: String?)
@kotlinx.serialization.Serializable
data class Participant(val role: List<Role>?, val member: Member?)
@kotlinx.serialization.Serializable
data class Member(val reference: String?, val display: String?)@kotlinx.serialization.Serializable
data class Role(val text: String?, val coding: List<Coding>?)
@kotlinx.serialization.Serializable
data class Coding(val system: String?, val code: String?, val display: String?)
@kotlinx.serialization.Serializable
data class ReasonCode(val coding: List<Coding>?)