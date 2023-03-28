package com.deepak.myapplication.android.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.AppBarWithTitle
import com.deepak.myapplication.android.viewPort
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewModel: HomeViewModel = koinViewModel()
    val viewModelState by viewModel.homeUiState.collectAsState()
    LaunchedEffect(key1 = lifecycle, block = {
        viewModel.getPatientDetails()
    })
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBarWithTitle(title = "Hapi Care")
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(modifier = Modifier.padding(viewPort), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(text = "UserId: ${viewModelState.userId}")
                Text(text = "UserId: ${viewModelState.patientId}")
                Text(text = "Patient Name: ${viewModelState.patientName?:""}")
            }
        }

    }
}