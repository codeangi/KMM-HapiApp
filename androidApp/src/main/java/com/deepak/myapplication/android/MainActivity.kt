package com.deepak.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepak.myapplication.AppConstant.ADD_NOTES_DEFAULT_TEXT
import com.deepak.myapplication.android.feature.appointments.*
import com.deepak.myapplication.android.feature.home.HomeScreen
import com.deepak.myapplication.android.feature.profile.ProfileScreen
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.model.CareTeamData
import com.deepak.myapplication.model.SelectedAppointmentData
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.White
                )
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
fun DashboardScreenView() {
    val navController = rememberNavController()
    val mainActivityViewModel = koinViewModel<MainActivityViewModel>()

    var selectedTab: MutableState<DashboardBottomNavScreen> = remember { mutableStateOf(DashboardBottomNavScreen.Home) }
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
                        selectedContentColor = customCyan,
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
            BottomBarNavigationGraph(navController = navController, mainActivityViewModel)
        }
    }
}

@Composable
fun BottomBarNavigationGraph(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel
) {
    NavHost(navController, startDestination = DashboardBottomNavScreen.Home.route) {
        composable(DashboardBottomNavScreen.Home.route) { HomeScreen() }
        composable(DashboardBottomNavScreen.Messages.route) { MessagesScreen() }
        composable(DashboardBottomNavScreen.Appointments.route) { AppointmentsScreen(
            onAddAppointmentClicked = {
                navController.navigate(Routes.APPOINTMENT_CARE_TEAM_SCREEN)
            },
            onAppointmentCardClicked = {
                mainActivityViewModel.selectedCareTeamDoctor = CareTeamData(doctorName = it.doctorName)
                mainActivityViewModel.selectedAppointmentData = SelectedAppointmentData(
                    it.doctorName,
                    it.symptoms,
                    "Office Visit",
                    it.location,
                    it.appointmentDate,
                    it.notes
                )
                navController.navigate(Routes.APPOINTMENT_DETAILS_SCREEN)
            }
        ) }
        composable(DashboardBottomNavScreen.Medications.route) { MedicationsScreen() }
        composable(DashboardBottomNavScreen.Profile.route) { ProfileScreen() }
        composable(Routes.APPOINTMENT_CARE_TEAM_SCREEN) {
            AppointmentCareTeamScreen (
                onBack = { navController.popBackStack() },
                onDoctorCardClick = {
                    mainActivityViewModel.selectedCareTeamDoctor = it
                    navController.navigate(Routes.DOCTOR_DETAILS_SCREEN)
                }
            )
        }
        composable(Routes.DOCTOR_DETAILS_SCREEN) {
            CareTeamDoctorDetailsScreen (
                onBack = {
                    navController.popBackStack()
                },
                onScheduleAppointmentClicked = {
                    navController.navigate(Routes.SCHEDULE_APPOINTMENT_FLOW_SCREEN)
                }
            )
        }

        composable(Routes.SCHEDULE_APPOINTMENT_FLOW_SCREEN) {
            ScheduleAppointmentFlowScreen(
                onBack = {
                    navController.popBackStack()
                },
                onAppointmentScheduleClicked = {
                    navController.navigate(Routes.SCHEDULE_APPOINTMENT_SUCCESS_SCREEN)
                }
            )
        }

        composable(Routes.SCHEDULE_APPOINTMENT_SUCCESS_SCREEN) {
            AppointmentScheduledSuccessScreen(
                onDoneClicked = {
                    navController.popBackStack(Routes.SCHEDULE_APPOINTMENT_FLOW_SCREEN, inclusive = true)
                    navController.navigate(DashboardBottomNavScreen.Appointments.route)
                    mainActivityViewModel.addedNotesState.value = ADD_NOTES_DEFAULT_TEXT
                },
                onViewDetailsClicked = {
                    navController.navigate(Routes.APPOINTMENT_DETAILS_SCREEN)
                }
            )
        }

        composable(Routes.APPOINTMENT_DETAILS_SCREEN) {
            val appointmentViewModel: AppointmentViewModel = koinViewModel()
            AppointmentReviewScreen(
                mainActivityViewModel = mainActivityViewModel,
                appointmentViewModel = appointmentViewModel,
                isScheduleButtonRequired = false,
                onScheduleAppointmentClick = {}
            ) {
                navController.popBackStack()
            }
        }
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
sealed class DashboardBottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : DashboardBottomNavScreen("home", "Home", Icons.Filled.Home)
    object Messages : DashboardBottomNavScreen("messages", "Messages", Icons.Filled.Email)
    object Appointments : DashboardBottomNavScreen("appointments", "Appointments", Icons.Filled.Call)
    object Medications : DashboardBottomNavScreen("medications", "Medications", Icons.Filled.Create)
    object Profile : DashboardBottomNavScreen("profile", "Profile", Icons.Filled.AccountCircle)
}

val bottomNavigationItems = listOf(
    DashboardBottomNavScreen.Home,
    DashboardBottomNavScreen.Messages,
    DashboardBottomNavScreen.Appointments,
    DashboardBottomNavScreen.Medications,
    DashboardBottomNavScreen.Profile
)

@Composable
fun MessagesScreen() {
    Text("Messages Screen")
}

@Composable
fun MedicationsScreen() {
    Text("Medications Screen")
}



