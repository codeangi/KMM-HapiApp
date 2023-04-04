package com.deepak.myapplication.model

data class TimeSlotData(
    val month: String? = null,
    val year: String? = null,
    val dayAndTimeMap: Pair<DateData, MutableList<String>>? = null
)

data class DateData(
    val weekDay: String? = null,
    val date: String? = null,
    val month: String? = null,
    val year: String? = null,
)