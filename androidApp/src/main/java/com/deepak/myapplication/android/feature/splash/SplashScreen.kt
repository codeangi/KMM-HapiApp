package com.deepak.myapplication.android.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(onCompleted:(String)->Unit) {
    val splashViewModel: SplashViewModel = koinViewModel()
    val screenState  by splashViewModel.screenNavState.collectAsState()
    if(screenState.isNotEmpty()){
        onCompleted.invoke(screenState)
        splashViewModel.clearScreenState()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Hapi Care", style = MaterialTheme.typography.h3.copy(color = Color.DarkGray))
    }
}