package com.deepak.myapplication.android.feature.appointments

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort

@Composable
fun SelectAppointmentTypeScreen(
    onAppointmentTypeSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(viewPort)) {
        Text(
            text = "What type of appointment would you like?",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.W600,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(Modifier.weight(1f)) {
            item {
                AppointmentTypeCardUi("Office Visit", R.drawable.outline_office_visit, onAppointmentTypeSelected)
                AppointmentTypeCardUi("Video Visit", R.drawable.outline_video_visit, onAppointmentTypeSelected)
            }
        }
    }
}

@Composable
fun AppointmentTypeCardUi(
    appointmentType: String,
    @DrawableRes iconResource: Int,
    onAppointmentTypeSelected: (String) -> Unit
) {
    OutlinedButton(
        onClick = { onAppointmentTypeSelected(appointmentType) },
        Modifier
            .padding(end = 12.dp)
            .heightIn(min = 64.dp),
        border = BorderStroke(1.dp, lightGrey),
        shape = RoundedCornerShape(16.dp)
    ){
        Icon(painter = painterResource(id = iconResource), contentDescription = null,
            Modifier
                .size(24.dp, 24.dp)
                .align(Alignment.CenterVertically),
            tint = customCyan
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text( text = appointmentType, style = MaterialTheme.typography.body1, fontWeight = FontWeight.W600, color = Color.Black, modifier = Modifier.weight(1f))
        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = null,
            Modifier
                .size(18.dp, 18.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Gray.copy(0.8f)
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}
