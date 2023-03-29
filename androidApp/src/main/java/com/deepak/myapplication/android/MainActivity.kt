package com.deepak.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deepak.myapplication.android.feature.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigator(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    var selectedTab: MutableState<Screen> = remember { mutableStateOf(Screen.Home) }
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {

                bottomNavigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title, fontSize = 9.sp, softWrap = false) },
                        selected = selectedTab.value == screen,
                        onClick = {
                            navController.navigate(screen.route) {

                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            selectedTab.value = screen
                        },
                        selectedContentColor = Color.Blue,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colors.background
        ) {
            BottomBarNavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBarNavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Messages.route) { MessagesScreen() }
        composable(Screen.Appointments.route) { AppointmentsScreen() }
        composable(Screen.Medications.route) { MedicationsScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Messages : Screen("messages", "Messages", Icons.Filled.Email)
    object Appointments : Screen("appointments", "Appointments", Icons.Filled.Call)
    object Medications : Screen("medications", "Medications", Icons.Filled.Create)
    object Profile : Screen("profile", "Profile", Icons.Filled.AccountCircle)
}

val bottomNavigationItems = listOf(
    Screen.Home,
    Screen.Messages,
    Screen.Appointments,
    Screen.Medications,
    Screen.Profile
)

@Composable
fun MessagesScreen() {
    Text("Messages Screen")
}

@Composable
fun AppointmentsScreen() {
    Text("Appointments Screen")
}

@Composable
fun MedicationsScreen() {
    Text("Medications Screen")
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen")
}

