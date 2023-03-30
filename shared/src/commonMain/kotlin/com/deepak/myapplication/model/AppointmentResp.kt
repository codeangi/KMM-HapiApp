package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class AppointmentResp(val resourceType:String, val id:String?, val entry: List<Entry>?)
