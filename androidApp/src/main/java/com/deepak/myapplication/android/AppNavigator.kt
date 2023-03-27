package com.deepak.myapplication.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepak.myapplication.android.feature.home.HomeScreen
import com.deepak.myapplication.android.feature.login.LoginScreen
import com.deepak.myapplication.android.feature.registration.UserRegistrationScreen

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN){
            LoginScreen(onRegCLick = {
                navController.navigate(Routes.REGISTRATION)
            }, onLoginSuccess = {
                navController.popBackStack()
                navController.navigate(Routes.HOME_SCREEN)
            })
        }
        composable(Routes.REGISTRATION) {
            UserRegistrationScreen(onBack = {
                navController.popBackStack()
            }, onRegSuccess = {
                navController.popBackStack(Routes.LOGIN,inclusive = true)
            })
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}