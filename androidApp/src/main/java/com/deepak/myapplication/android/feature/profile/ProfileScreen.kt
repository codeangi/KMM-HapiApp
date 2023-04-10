package com.deepak.myapplication.android.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.viewPort
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinViewModel()
    val viewModelState by viewModel.profileUiState.collectAsState()

    Scaffold { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(viewPort)
            ) {
                Column(
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Name : ${viewModelState.patientName}",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.fillMaxWidth(),
                        color = customCyan
                    )
                    Text(
                        text = "Patient ID : ${viewModelState.patientId}",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.fillMaxWidth(),
                        color = customCyan
                    )
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customCyan)
                ) {
                    Text(
                        text = "LOGOUT",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.W700,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}