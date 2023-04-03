package com.deepak.myapplication.model

data class TimeSlotData(
    val month: String? = null,
    val year: String? = null,
    val dayAndTimeMap: HashMap<String, List<String>>? = hashMapOf()
)
