package com.deepak.myapplication.android.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(onRegCLick: () -> Unit, onLoginSuccess: () -> Unit) {
    val viewModel: LoginViewModel = koinViewModel()
    val screenState by viewModel.viewModelState.collectAsState()
    when (screenState.loginState) {
        LoginState.Success -> {
            onLoginSuccess.invoke()
            viewModel.clearLoginState()
        }
        is LoginState.Error -> {
            //Show error
        }
        else -> {

        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = screenState.userName, onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.OnUserNameChange(it))
                }, placeholder = {
                    Text(text = "User name")
                })
                OutlinedTextField(value = screenState.password, onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.OnPasswordChange(it))
                }, placeholder = {
                    Text(text = "Password")
                }, visualTransformation = PasswordVisualTransformation())

                Button(onClick = { viewModel.onEvent(LoginScreenEvent.OnLoginAction) }) {
                    Text(text = "Submit")
                }

                TextButton(onClick = {
                    onRegCLick.invoke()
                }) {
                    Text(
                        text = "New User?",
                        style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}