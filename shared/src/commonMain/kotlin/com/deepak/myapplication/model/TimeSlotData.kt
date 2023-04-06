package com.deepak.myapplication.model

data class TimeSlotData(
    val month: String? = null,
    val showMonth: Boolean = false,
    val year: String? = null,
    val dayAndTimeMap: Pair<DateData, MutableList<TimeWithResponseData>>? = null,
    val requestBodyForBooking: Resource? = null
)

data class DateData(
    val weekDay: String? = null,
    val date: String? = null,
    val month: String? = null,
    val year: String? = null,
)

data class TimeWithResponseData(
    val time: String? = null,
    val response: Resource? = null
)