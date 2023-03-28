package com.deepak.myapplication.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepak.myapplication.android.feature.home.HomeScreen
import com.deepak.myapplication.android.feature.login.LoginScreen
import com.deepak.myapplication.android.feature.registration.UserRegistrationScreen
import com.deepak.myapplication.android.feature.splash.SplashScreen

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.SPLASH_SCREEN
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SPLASH_SCREEN){
            SplashScreen(onCompleted = {
                navController.popBackStack()
                navController.navigate(it)
            })
        }
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
                navController.navigate(Routes.HOME_SCREEN)
            })
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}