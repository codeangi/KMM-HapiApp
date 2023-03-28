package com.deepak.myapplication.android.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.android.Routes
import com.deepak.myapplication.usecase.HomeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel constructor(private val homeUseCase: HomeUseCase): ViewModel() {
    fun clearScreenState() {
        screenNavState.value = ""
    }

    var screenNavState = MutableStateFlow("")
    private set
    init {
     viewModelScope.launch {
         delay(1000)
         val patientId = homeUseCase.getUserId()
         val userId = homeUseCase.getPatientId()
         if(patientId.isNullOrEmpty() || userId.isNullOrEmpty()){
             screenNavState.value = Routes.LOGIN
         }else {
             screenNavState.value = Routes.HOME_SCREEN
         }
     }
    }
}