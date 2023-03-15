package com.deepak.myapplication.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepak.myapplication.android.feature.registration.UserRegistrationScreen

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.REGISTRATION
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.REGISTRATION) {
            UserRegistrationScreen()
        }
    }
}