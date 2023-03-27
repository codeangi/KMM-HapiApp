package com.deepak.myapplication.android.feature.login

import androidx.lifecycle.ViewModel
import com.deepak.myapplication.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow

data class LoginViewModelState(
    val userName: String = "",
    val password: String = "",
    val showBlockingProgress: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

sealed interface LoginScreenEvent {
    data class OnUserNameChange(val userName: String): LoginScreenEvent
    data class OnPasswordChange(val password: String): LoginScreenEvent
    object OnLoginAction: LoginScreenEvent

}

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    var viewModelState = MutableStateFlow(LoginViewModelState())
    private set
    fun onEvent(event: LoginScreenEvent) {
        when(event){
            is LoginScreenEvent.OnUserNameChange ->{
                    viewModelState.value = viewModelState.value.copy(userName = event.userName)
            }
            is LoginScreenEvent.OnPasswordChange ->{
                viewModelState.value = viewModelState.value.copy(password = event.password)
            }
            is LoginScreenEvent.OnLoginAction ->{

            }
            else->{}
        }
    }
}