package com.deepak.myapplication.android.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen() {

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues), contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxWidth()) {
                //OutlinedTextField(value = , onValueChange = )
            }
        }
    }
}