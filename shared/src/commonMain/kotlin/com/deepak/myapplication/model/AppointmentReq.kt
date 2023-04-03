package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class AppointmentReq(val resourceType:String, val parameter: List<Parameter>)
