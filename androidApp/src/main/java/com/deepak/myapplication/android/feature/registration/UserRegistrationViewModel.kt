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
    val isActionEnabled: Boolean = false
)


sealed interface UserRegistrationScreenEvent {
    data class OnNameChange(val name: String) : UserRegistrationScreenEvent
    data class OnPasswordChange(val password: String) : UserRegistrationScreenEvent
    data class OnEmailChange(val email: String) : UserRegistrationScreenEvent
    object SignUp : UserRegistrationScreenEvent
}

class UserRegistrationViewModel(private val userRegistrationUseCase: UserRegistrationUseCase) : ViewModel() {
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
        val user = User(name = state.name, email = state.email, password = state.password, 1)
        viewModelScope.launch {
            val status = userRegistrationUseCase.registerUser(user)
            Log.d("ViewModel", "User data inserted:$status")
        }
    }

    private fun isValidData() {
        val state = viewModelStateFlow.value
        val isActionActive =
            (state.email.isNotEmpty() && state.name.isNotEmpty() && state.password.isNotEmpty())
        viewModelStateFlow.value = viewModelStateFlow.value.copy(isActionEnabled = isActionActive)
    }
}