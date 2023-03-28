package com.deepak.myapplication.android.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.PatientDataResp
import com.deepak.myapplication.usecase.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class HomeViewModelState(
    val userId: String = "",
    val patientId: String = "",
    val patientName: String? = ""


)

class HomeViewModel constructor(private val homeUseCase: HomeUseCase) : ViewModel() {

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
            Log.d("HomeViewModel","Fetching patient details")
            val data = homeUseCase.getPatientDetails()
            Log.d("HomeViewModel","patient details:$data")
            if (data is AppRequest.Result<*> && data.result is PatientDataResp) {
                (data.result as PatientDataResp).name?.firstOrNull()?.let { nameData ->
                    val name = nameData.given?.reduce { startName, name -> "$startName $name" }
                    Log.d("HomeViewModel","patient name:$name")
                    homeUiState.value = homeUiState.value.copy(patientName = name)
                }
            }
        }
    }
}