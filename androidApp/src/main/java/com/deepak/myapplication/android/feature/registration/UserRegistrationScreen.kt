package com.deepak.myapplication.android.feature.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.AppBarOnlyBackButton
import org.koin.androidx.compose.koinViewModel


@Composable
fun UserRegistrationScreen(onBack: () -> Unit, onRegSuccess: () -> Unit) {
    val viewModel: UserRegistrationViewModel = koinViewModel()
    val viewModelState = viewModel.viewModelStateFlow.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBarOnlyBackButton(onBack)
    }) { contentPadding ->
        Box(
            modifier = Modifier
               .fillMaxSize()
               .padding(contentPadding), contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                OutlinedTextField(value = viewModelState.value.name, onValueChange = {
                    viewModel.onEvent(UserRegistrationScreenEvent.OnNameChange(it))
                }, modifier = Modifier.fillMaxWidth(), placeholder = {
                    Text(text = "Name")
                }, label = {
                    Text(text = "Name")
                })

                OutlinedTextField(value = viewModelState.value.email, onValueChange = {
                    viewModel.onEvent(UserRegistrationScreenEvent.OnEmailChange(it))
                }, modifier = Modifier.fillMaxWidth(), placeholder = {
                    Text(text = "EMail")
                }, label = {
                    Text(text = "EMail")
                })

                OutlinedTextField(
                    value = viewModelState.value.password,
                    onValueChange = {
                        viewModel.onEvent(UserRegistrationScreenEvent.OnPasswordChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = {
                        Text(text = "Password")
                    }, label = {
                        Text(text = "Password")
                    }
                )

                Button(onClick = {
                    viewModel.onEvent(UserRegistrationScreenEvent.SignUp)
                }, enabled = viewModelState.value.isActionEnabled) {
                    Text(text = "Submit", modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }
    }
}