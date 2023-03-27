package com.deepak.myapplication.android.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.myapplication.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class LoginViewModelState(
    val userName: String = "",
    val password: String = "",
    val showBlockingProgress: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val loginState: LoginState = LoginState.None
)

sealed interface LoginScreenEvent {
    data class OnUserNameChange(val userName: String): LoginScreenEvent
    data class OnPasswordChange(val password: String): LoginScreenEvent
    object OnLoginAction: LoginScreenEvent
}

sealed interface LoginState{
    object Success: LoginState
    data class Error(val errorMessage:String): LoginState
    object None: LoginState
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
                val userName = viewModelState.value.userName
                val password = viewModelState.value.password
                viewModelScope.launch {
                    loginUseCase.loginUser(email = userName, password = password)?.let {
                        viewModelState.value = viewModelState.value.copy(loginState = LoginState.Success)
                    }?: kotlin.run {
                        viewModelState.value = viewModelState.value.copy(loginState = LoginState.Error("Not found the user"))
                    }
                }
            }
        }
    }

    fun clearLoginState(){
        viewModelState.value = viewModelState.value.copy(loginState = LoginState.None)
    }
}