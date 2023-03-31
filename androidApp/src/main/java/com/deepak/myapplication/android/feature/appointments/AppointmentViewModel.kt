package com.deepak.myapplication.android.feature.appointments

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.AppointmentScheduleData
import com.deepak.myapplication.model.CareTeamData
import com.deepak.myapplication.model.ClinicData
import com.deepak.myapplication.model.DoctorData
import com.deepak.myapplication.usecase.AppointmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel constructor(private val appointmentUseCase: AppointmentUseCase) : ViewModel() {

    var todaysAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var upcomingAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var pastAppointments = mutableStateOf<List<AppointmentScheduleData>>(emptyList())

    var careTeamDataState = mutableStateOf<List<CareTeamData>>(emptyList())

    fun getPatientAppointmentSchedules() {
        viewModelScope.launch {
            val data = appointmentUseCase.getPatientAppointmentSchedules()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    val resultList = data.result.filterIsInstance<AppointmentScheduleData>().takeIf { it.size == data.result.size } ?: emptyList()
                    todaysAppointments.value = listOf(resultList.first())
                    upcomingAppointments.value = resultList.subList(3, 5)
                    pastAppointments.value = resultList.subList(1, 3)
                }
            }
        }
    }

    fun getMyCareTeamData() {
        viewModelScope.launch {
            val data = appointmentUseCase.getMyCareTeamData()
            if (data is AppRequest.ListResult<*>) {
                data.result.let {
                    careTeamDataState.value = data.result.filterIsInstance<CareTeamData>().takeIf { it.size == data.result.size } ?: emptyList()
                }
            }
        }
    }
}