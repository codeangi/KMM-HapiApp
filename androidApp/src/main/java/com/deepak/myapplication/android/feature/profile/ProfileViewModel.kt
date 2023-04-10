package com.deepak.myapplication.android.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.android.feature.home.HomeViewModelState
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.PatientDataResp
import com.deepak.myapplication.usecase.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    var profileUiState = MutableStateFlow(HomeViewModelState())
        private set

    init {
        getPatientId()
        viewModelScope.launch(Dispatchers.IO) {
            profileUiState.value = profileUiState.value.copy(
                userId = profileUseCase.getUserId() ?: "",
                patientId = profileUseCase.getPatientId() ?: ""
            )
        }
    }

    private fun getPatientId() {
        viewModelScope.launch {
            val data = profileUseCase.getPatientDetails()
            if (data is AppRequest.Result<*> && data.result is PatientDataResp) {
                (data.result as PatientDataResp).name?.firstOrNull()?.let { nameData ->
                    val name = nameData.given?.reduce { startName, name -> "$startName $name" }
                    profileUiState.value = profileUiState.value.copy(patientName = name)
                }
            }
        }
    }
}
