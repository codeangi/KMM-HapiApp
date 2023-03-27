package com.deepak.myapplication.android.feature.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.usecase.UserRegistrationUseCase
import comdeepakmyapplicationlocal.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class UserRegistrationViewModelState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isActionEnabled: Boolean = false,
    val registrationState: RegistrationState = RegistrationState.None
)

sealed interface RegistrationState{
    object Success: RegistrationState
    data class Error(val errorMessage:String): RegistrationState
    object None: RegistrationState
}

sealed interface UserRegistrationScreenEvent {
    data class OnNameChange(val name: String) : UserRegistrationScreenEvent
    data class OnPasswordChange(val password: String) : UserRegistrationScreenEvent
    data class OnEmailChange(val email: String) : UserRegistrationScreenEvent
    object SignUp : UserRegistrationScreenEvent
}

class UserRegistrationViewModel constructor(private val userRegistrationUseCase: UserRegistrationUseCase) : ViewModel() {
    var viewModelStateFlow = MutableStateFlow(UserRegistrationViewModelState())
        private set

    fun onEvent(event: UserRegistrationScreenEvent) {
        when (event) {
            is UserRegistrationScreenEvent.OnNameChange -> {
                viewModelStateFlow.value = viewModelStateFlow.value.copy(name = event.name)
                isValidData()
            }
            is UserRegistrationScreenEvent.OnPasswordChange -> {
                viewModelStateFlow.value = viewModelStateFlow.value.copy(password = event.password)
                isValidData()
            }
            is UserRegistrationScreenEvent.OnEmailChange -> {
                viewModelStateFlow.value = viewModelStateFlow.value.copy(email = event.email)
                isValidData()
            }
            is UserRegistrationScreenEvent.SignUp -> {
                handleSignUpRequest()
            }
        }
    }

    private fun handleSignUpRequest() {
        val state = viewModelStateFlow.value
        val user = User(name = state.name, email = state.email, password = state.password, 1, "")
        viewModelScope.launch {
            val status = userRegistrationUseCase.registerUser(user)
            if(status){
                viewModelStateFlow.value = viewModelStateFlow.value.copy(registrationState = RegistrationState.Success)
            }else {
                viewModelStateFlow.value = viewModelStateFlow.value.copy(registrationState = RegistrationState.Error("User existing with this email"))
            }
            Log.d("ViewModel", "User data inserted:$status")
        }
    }

    fun clearRegState(){
        viewModelStateFlow.value = viewModelStateFlow.value.copy(registrationState = RegistrationState.None)
    }

    private fun isValidData() {
        val state = viewModelStateFlow.value
        val isActionActive =
            (state.email.isNotEmpty() && state.name.isNotEmpty() && state.password.isNotEmpty())
        viewModelStateFlow.value = viewModelStateFlow.value.copy(isActionEnabled = isActionActive)
    }
}