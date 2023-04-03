package com.deepak.myapplication.model


data class SelectedAppointmentData(
    var doctorName: String? = null,
    var reason: String? = null,
    var appointmentType: String? = null,
    var appointmentLocationAddress: String? = null,
    var timeSlotData: String? = null,
)
