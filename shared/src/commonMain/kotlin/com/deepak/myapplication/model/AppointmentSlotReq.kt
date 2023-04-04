package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class AppointmentSlotReq(val resourceType:String, val parameter: List<Parameter>)
