package com.deepak.myapplication.infra

object RemoteRoutes {
    val BASE_URL = "https://hapi.fhir.org/baseR4/"
    fun GET_PATIENT_DETAILS(patientId:String):String{
       return BASE_URL+ "Patient/$patientId"
    }
}