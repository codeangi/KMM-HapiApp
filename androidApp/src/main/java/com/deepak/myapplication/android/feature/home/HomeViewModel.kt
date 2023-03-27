package com.deepak.myapplication.android.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.usecase.HomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class HomeViewModelState(
    val userId:String = "",
    val patientId:String = ""

)
class HomeViewModel constructor(homeUseCase: HomeUseCase): ViewModel() {

    var homeUiState = MutableStateFlow(HomeViewModelState())
    private set
    init {
        viewModelScope.launch {
            homeUiState.value = homeUiState.value.copy(userId = homeUseCase.getUserId()?:"", patientId = homeUseCase.getPatientId()?:"")
        }
    }

}