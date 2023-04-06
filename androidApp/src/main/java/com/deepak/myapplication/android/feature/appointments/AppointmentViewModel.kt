package com.deepak.myapplication.android.feature.appointments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.*
import com.deepak.myapplication.usecase.AppointmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel constructor(private val appointmentUseCase: AppointmentUseCase) : ViewModel() {

    var todaysAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var upcomingAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var pastAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var careTeamDataState = mutableStateOf<List<CareTeamData>>(emptyList())

    var timeSlotDataState = mutableStateOf<List<TimeSlotData>>(emptyList())

    var appointmentBookingSuccess = mutableStateOf(false)

    var appointmentBookingError = mutableStateOf(false)

    var isApiLoading = MutableStateFlow(false)

    fun getPatientAppointmentSchedules() {
        isApiLoading.value = true
        getPastAppointments()
        getTodaysAppointments()
        getFutureAppointments()
    }

    private fun getFutureAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = appointmentUseCase.getPatientFutureAppointments()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    val resultList = data.result.filterIsInstance<AppointmentScheduleData>().takeIf { it.size == data.result.size } ?: emptyList()
                    upcomingAppointments.value = resultList
                    isApiLoading.value = false

                }
            }
        }
    }

    private fun getTodaysAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = appointmentUseCase.getPatientTodaysAppointments()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    val resultList = data.result.filterIsInstance<AppointmentScheduleData>().takeIf { it.size == data.result.size } ?: emptyList()
                    todaysAppointments.value = resultList
                    isApiLoading.value = false

                }
            }
        }
    }

    private fun getPastAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = appointmentUseCase.getPatientPastAppointments()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    val resultList = data.result.filterIsInstance<AppointmentScheduleData>().takeIf { it.size == data.result.size } ?: emptyList()
                    pastAppointments.value = resultList
                    isApiLoading.value = false

                }
            }
        }
    }

    fun getMyCareTeamData() {
        isApiLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = appointmentUseCase.getMyCareTeamData()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    careTeamDataState.value = data.result.filterIsInstance<CareTeamData>().takeIf { it.size == data.result.size } ?: emptyList()
                    isApiLoading.value = false
                }
            }
        }
    }

    fun getAppointmentReasonsList(): List<String> {
        return listOf(
            "Back Pain",
            "Headache",
            "Migrane"
        )
    }

    fun getAppointmentTimeSlots(practitionerId: String) {
        isApiLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = appointmentUseCase.getAppointmentSlots(practitionerId)
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    timeSlotDataState.value = data.result.filterIsInstance<TimeSlotData>().takeIf { it.size == data.result.size } ?: emptyList()
                    isApiLoading.value = false

                }
            }
        }
    }

    fun bookAppointment(resource: BookingResource) {
        isApiLoading.value = true
        viewModelScope.launch {
            val data = appointmentUseCase.bookAppointment(resource)
            if (data is AppRequest.Result<*>) {
                appointmentBookingSuccess.value = true
            } else {
                appointmentBookingError.value = true
            }
            isApiLoading.value = false
        }
    }
}
