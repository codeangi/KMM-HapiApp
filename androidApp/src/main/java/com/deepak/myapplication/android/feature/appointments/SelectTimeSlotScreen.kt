package com.deepak.myapplication.android.feature.appointments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.MainActivityViewModel
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.Resource
import com.deepak.myapplication.model.TimeSlotData

var currentMonth: String? = null

@Composable
fun SelectTimeSlotScreen(
    appointmentViewModel: AppointmentViewModel,
    mainActivityViewModel: MainActivityViewModel,
    onClickOfTimeSlot: (TimeSlotData, String, Resource?) -> Unit
) {

    val timeSlotList: MutableState<List<TimeSlotData>> = appointmentViewModel.timeSlotDataState
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(key1 = lifecycle, block = {
        mainActivityViewModel.selectedCareTeamDoctor?.practitionerId?.let {
            appointmentViewModel.getAppointmentTimeSlots(it)
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            currentMonth = null
        }
    }

    Column(modifier = Modifier.padding(viewPort)) {
        Text(
            text = "When would you like to be seen?",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.W600,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider(Modifier.height(1.dp), color = lightGrey)

        LazyColumn {
            items(timeSlotList.value) {
                DateTimeSlotRowUi(
                   it, onClickOfTimeSlot
                )
            }
        }
    }
}

@Composable
fun DateTimeSlotRowUi(timeSlotData: TimeSlotData, onClickOfTimeSlot: (TimeSlotData, String, Resource?) -> Unit) {
    if (timeSlotData.month != currentMonth) {
        MonthDataUi(timeSlotData.month ?: "", timeSlotData.year ?: "")
        currentMonth = timeSlotData.month
        Divider(Modifier.height(1.dp), color = lightGrey)
    }
    Column {
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
           Column {
               Text(text = timeSlotData.dayAndTimeMap?.first?.weekDay ?: "",  style = MaterialTheme.typography.body1, fontWeight = FontWeight.W600, color = Color.Black)
               Spacer(modifier = Modifier.height(4.dp))
               Text(text = timeSlotData.dayAndTimeMap?.first?.date ?: "",  style = MaterialTheme.typography.h6, color = Color.Black)
           }
            Spacer(modifier = Modifier.width(16.dp))
            LazyRow {
                timeSlotData.dayAndTimeMap?.second?.toList()?.let {
                    items(it) { time ->
                        time.time?.let {
                            TimeDataUi(it) { selectedTime ->
                            onClickOfTimeSlot(timeSlotData, selectedTime, time.response)
                            currentMonth = null
                        }
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Divider(Modifier.height(1.dp), color = lightGrey)
}

@Composable
fun TimeDataUi(time: String, onTimeSlotClicked: (String) -> Unit) {
    Button(
        onClick = { onTimeSlotClicked(time) },
        modifier = Modifier
            .size(110.dp, 48.dp)
            .padding(end = 8.dp)
            .background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(
            text = time,
            modifier = Modifier.background(Color.Transparent),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.W600,
            softWrap = false,
            color = customCyan,
        )
    }
}

@Composable
fun MonthDataUi(month: String, year: String) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$month, $year",  style = MaterialTheme.typography.h5, fontWeight = FontWeight.W600, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
