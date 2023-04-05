package com.deepak.myapplication.android.feature.appointments

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.AppointmentScheduleData
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppointmentsScreen(
    onAddAppointmentClicked: () -> Unit,
    onAppointmentCardClicked: (AppointmentScheduleData) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewModel: AppointmentViewModel = koinViewModel()
    val isLoading by viewModel.isApiLoading.collectAsState()

    LaunchedEffect(key1 = lifecycle, block = {
        viewModel.getPatientAppointmentSchedules()
    })
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                    Modifier.size(48.dp)
                        .align(Center),
                    color = Color.Black
                )
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(viewPort)) {
                AddAppointmentButtonUi(
                    modifier = Modifier.align(Alignment.End),
                    onAddAppointmentClicked
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    item {
                        Text(text = "Appointments", style = MaterialTheme.typography.h4, fontWeight = FontWeight.W700, color = Color.Black)
                    }

                    if (viewModel.todaysAppointments.value.isEmpty().not()) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Today",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.W700,
                                color = Color.Black
                            )
                        }
                    }

                    items(viewModel.todaysAppointments.value) {
                        AppointmentCard(it, onAppointmentCardClicked)
                    }

                    if (viewModel.upcomingAppointments.value.isEmpty().not()) {
                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Upcoming",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.W700,
                                color = Color.Black
                            )
                        }
                    }
                    items(viewModel.upcomingAppointments.value) {
                        AppointmentCard(it, onAppointmentCardClicked)
                    }

                    if (viewModel.pastAppointments.value.isEmpty().not()) {
                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Past",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.W700,
                                color = Color.Black
                            )
                        }
                    }
                    items(viewModel.pastAppointments.value) {
                        AppointmentCard(it, onAppointmentCardClicked)
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointmentScheduleData: AppointmentScheduleData, onAppointmentCardClicked: (AppointmentScheduleData) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAppointmentCardClicked.invoke(appointmentScheduleData) }
            .height(320.dp)
            .padding(end = 16.dp)
            .border(
                width = 1.dp,
                color = lightGrey,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.clinic),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                contentScale = ContentScale.FillBounds
            )
            Box(
                Modifier
                    .clip(shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .weight(1f)) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = appointmentScheduleData.symptoms ?: "",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.W700,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = null,
                            Modifier
                                .size(24.dp, 24.dp)
                                .align(Alignment.CenterVertically),
                            tint = lightGrey)

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    TextWithPrecedingIcon(appointmentScheduleData.appointmentDate ?: "", R.drawable.outline_date_icon)

                    Spacer(modifier = Modifier.height(8.dp))

                    TextWithPrecedingIcon(appointmentScheduleData.doctorName ?: "", R.drawable.outline_person_icon)

                    Spacer(modifier = Modifier.height(8.dp))

                    TextWithPrecedingIcon(appointmentScheduleData.location ?: "", R.drawable.outline_location_icon)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TextWithPrecedingIcon(description: String, @DrawableRes icon: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = icon), contentDescription = null,
            Modifier
                .size(24.dp, 24.dp)
                .align(Alignment.CenterVertically),
            tint = customCyan)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = description, style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun AddAppointmentButtonUi(
    modifier: Modifier,
    onAddAppointmentClicked: () -> Unit
) {
    Button(
        onClick = { onAddAppointmentClicked.invoke() },
        modifier = modifier
            .heightIn(min = 48.dp)
            .wrapContentWidth(),
        colors =  ButtonDefaults.buttonColors(
            backgroundColor = customCyan
        ),
        shape = RoundedCornerShape(32.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
            Text(
                text = "ADD",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }

}
