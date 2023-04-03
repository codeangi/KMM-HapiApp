package com.deepak.myapplication.android.feature.appointments

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.MainActivityViewModel
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort

@Composable
fun AppointmentReviewScreen(
    mainActivityViewModel: MainActivityViewModel,
    onScheduleAppointmentClick: () -> Unit
) {
    val appointmentData = mainActivityViewModel.selectedAppointmentData
    Column(modifier = Modifier.padding(viewPort)) {
        Text(
            text = "Appointment Review",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.W600,
            color = Color.Black,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "This time is reserved for you until 3:29 PM. Please complete scheduling by then.",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
        )

        ReviewDataCardUi("Provider", mainActivityViewModel.selectedCareTeamDoctor?.doctorName ?: "", R.drawable.outline_person_icon)
        ReviewDataCardUi("Reason for visit", appointmentData.reason ?: "", R.drawable.outline_visit_reason)
        ReviewDataCardUi("Location", appointmentData.appointmentLocationAddress ?: "", R.drawable.outline_location_icon)
        ReviewDataCardUi("Date and time", appointmentData.timeSlotData ?: "", R.drawable.outline_date_icon)
        ReviewDataCardUi("Notes", "Add notes to share with your care team ahead of your visit.", R.drawable.outline_note_24)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onScheduleAppointmentClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = viewPort),
            colors = ButtonDefaults.buttonColors(backgroundColor = customCyan)
        ) {
            Text(text = "SCHEDULE APPOINTMENT", style = MaterialTheme.typography.body1,fontWeight = FontWeight.W700, color = Color.White, modifier = Modifier.padding(horizontal = viewPort))
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ReviewDataCardUi(category: String, data: String, @DrawableRes icon: Int) {

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(CenterVertically),

                tint = customCyan
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W700,
                    color = Color.Black
                )
                Text(
                    text = data,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray.copy(0.8f),
                    maxLines = 2
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(Modifier.height(1.dp), color = lightGrey)
    }
}
