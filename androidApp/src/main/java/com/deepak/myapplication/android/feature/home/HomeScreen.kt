package com.deepak.myapplication.android.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.deepak.myapplication.android.AppBarWithTitle
import com.deepak.myapplication.android.viewPort
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val viewModelState by viewModel.homeUiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBarWithTitle(title = "Hapi Care")
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(modifier = Modifier.padding(viewPort)) {
                Text(text = "UserId: ${viewModelState.userId}")
                Text(text = "UserId: ${viewModelState.patientId}")
            }
        }

    }
}