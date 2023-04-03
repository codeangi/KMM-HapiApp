package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class CareTeamData(
    val doctorName: String? = null,
    val designation: String? = null,
    val hospitalLocation: String? = null,
    val image: String? = null,
    val doctorDescription: String? = null,
    val gender: String? = null,
    val languages: String? = null,
    val yearsOfPractice: String? = null,
    val boardCertification: String? = null,
    val groupAffiliations: String? = null,
    val hospitalAffiliations: String? = null,
    val medicalSchool: String? = null,
    val residency: String? = null,
    val locationAddress: String? = null,
    val locationImage: String? = null
)
