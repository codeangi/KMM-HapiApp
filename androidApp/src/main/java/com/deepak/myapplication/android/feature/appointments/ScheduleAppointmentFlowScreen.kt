package com.deepak.myapplication.android.feature.appointments
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.deepak.myapplication.android.MainActivityViewModel
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.TimeSlotData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScheduleAppointmentFlowScreen(onBack: () -> Unit, onAppointmentScheduleClicked: () -> Unit) {
    // Define a count of steps
    val stepsCount = 6

    // Define the current step
    var currentStep = remember { mutableStateOf(0) }

    val appointmentViewModel: AppointmentViewModel = koinViewModel()

    val mainActivityViewModel: MainActivityViewModel = koinViewModel()

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = lifecycle, block = {
        appointmentViewModel.getAppointmentTimeSlots()
    })

    Scaffold(
        topBar = {
            Column {
                AppBarWithBackButtonTitleAndCancelCTA(
                    onBack,
                    currentStep
                )
                LinearProgressIndicator(
                    progress = (currentStep.value + 1).toFloat() / stepsCount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = viewPort),
                    color = customCyan
                )
            }
        }
    ) {
        val modifier = Modifier.padding(it)
        Box(modifier = modifier) {
            // Create the content for each step
            when (currentStep.value) {
                0 -> {
                    SelectReasonScreen(
                        appointmentViewModel.getAppointmentReasonsList()
                    ) {
                        mainActivityViewModel.selectedAppointmentData.reason = it
                        currentStep.value++
                    }
                }
                1 -> {
                    SelectAppointmentTypeScreen() {
                        mainActivityViewModel.selectedAppointmentData.appointmentType = it
                        currentStep.value++
                    }
                }
                2 -> {
                    SelectLocationScreen() {
                        mainActivityViewModel.selectedAppointmentData.appointmentLocationAddress = it.address
                        currentStep.value++
                    }
                }
                3 -> {
                    SelectTimeSlotScreen(
                        appointmentViewModel.timeSlotDataState
                    ) { timeSlotData, selectedTime ->
                        mainActivityViewModel.selectedAppointmentData.timeSlotData = getSelectedTimeData(timeSlotData, selectedTime)
                        currentStep.value++
                    }
                }
                4 -> {
                    AppointmentReviewScreen(mainActivityViewModel) {
                        onAppointmentScheduleClicked()
                    }
                }
            }
        }
    }
}

fun getSelectedTimeData(timeSlotData: TimeSlotData, selectedTime: String): String? {
    val date = timeSlotData.dayAndTimeMap?.keys?.firstOrNull()
    val day = "Tuesday"
    return "$day, ${timeSlotData.month} $date, $selectedTime"

}


@Composable
fun AppBarWithBackButtonTitleAndCancelCTA(
    onBack: () -> Unit,
    currentStep: MutableState<Int>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(viewPort),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                if (currentStep.value > 0)
                    currentStep.value--
                else
                    onBack.invoke()
            })

        Text(
            text = "Schedule Appointment",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W700,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )

        Text(text = "CANCEL", style = MaterialTheme.typography.body1, color = customCyan, modifier = Modifier.clickable { onBack.invoke() })

    }
}
