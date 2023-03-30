package com.deepak.myapplication.infra

//private const val HAPI_BASE_URL = "https://hapi.fhir.org/baseR4/"
private const val YCARE_BASE_URL = "https://ycares-dev-hapi.ymedia.in/fhir/"
const val BASE_URL = YCARE_BASE_URL
object RemoteRoutes {

    fun GET_PATIENT_DETAILS(patientId:String):String{
       return BASE_URL+ "Patient/$patientId"
    }

    const val GET_PATIENT_CARE_TEAM = BASE_URL +"CareTeam"

    object Parameters{
        const val Participant = "participant"
    }
}