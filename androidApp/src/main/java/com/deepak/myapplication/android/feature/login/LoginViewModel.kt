package com.deepak.myapplication.android.feature

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class LoginViewModelState(
    val userName: String = "",
    val password: String = "",
    val showBlockingProgress: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

sealed interface LoginScreenEvent {
    data class OnUserNameChange(val userName: String):LoginScreenEvent
    data class OnPasswordChange(val password: String):LoginScreenEvent
    object OnLoginAction:LoginScreenEvent

}

class LoginViewModel : ViewModel() {
    val viewModelState = MutableStateFlow(LoginViewModelState())

    fun onEvent(event: LoginScreenEvent) {
        when(event){
            is LoginScreenEvent.OnUserNameChange->{
                    viewModelState.value = viewModelState.value.copy(userName = event.userName)
            }
            is LoginScreenEvent.OnPasswordChange->{
                viewModelState.value = viewModelState.value.copy(password = event.password)
            }
            is LoginScreenEvent.OnLoginAction->{

            }
            else->{}
        }
    }
}