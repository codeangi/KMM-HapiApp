package com.deepak.myapplication.android.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.PatientDataResp
import com.deepak.myapplication.usecase.AppointmentUseCase
import com.deepak.myapplication.usecase.HomeUseCase
import com.deepak.myapplication.usecase.KMPPractitionerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class HomeViewModelState(
    val userId: String = "",
    val patientId: String = "",
    val patientName: String? = ""


)

class HomeViewModel constructor(
    private val homeUseCase: HomeUseCase,
    private val appointmentUseCase: AppointmentUseCase
) : ViewModel() {

    var homeUiState = MutableStateFlow(HomeViewModelState())
        private set

    init {
        viewModelScope.launch {
            homeUiState.value = homeUiState.value.copy(
                userId = homeUseCase.getUserId() ?: "",
                patientId = homeUseCase.getPatientId() ?: ""
            )
        }
    }

    fun getPatientDetails() {
        viewModelScope.launch(Dispatchers.IO) {

            val data = homeUseCase.getPatientDetails()
            Log.d("HomeViewModel", "patient details:$data")
            if (data is AppRequest.Result<*> && data.result is PatientDataResp) {
                (data.result as PatientDataResp).name?.firstOrNull()?.let { nameData ->
                    val name = nameData.given?.reduce { startName, name -> "$startName $name" }
                    Log.d("HomeViewModel", "patient name:$name")
                    homeUiState.value = homeUiState.value.copy(patientName = name)
                }
            }
            val careData = homeUseCase.getPatientCareTeam()
            Log.d("HomeViewModel", "patient care details:$careData")
            val accessTokenData = homeUseCase.getAccessToken()
            Log.d("HomeViewModel", "Access TokenData:$accessTokenData")
            val appointmentData = appointmentUseCase.getPatientPastAppointments()
            Log.d("HomeViewModel", "Appointment Data:$appointmentData")
            val practitionerId = "1ed22585-3ea1-3c61-b485-f83c940a8880"
            val practitionerDetails =
                KMPPractitionerUseCase().practitionerUseCase.getPractitionerDetails(practitionerId = practitionerId)
            Log.d("HomeViewModel", "PractitionerDetails:$practitionerDetails")

        }
    }
}