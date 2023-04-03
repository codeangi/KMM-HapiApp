package com.deepak.myapplication.android.feature.appointments


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.viewPort

@Composable
fun AppointmentScheduledSuccessScreen(
    onDoneClicked: () -> Unit,
    onViewDetailsClicked: () -> Unit
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.successful_animation)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        speed = 1f
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(viewPort),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier.size(300.dp)
                )
                Text(
                    text = "Appointment Scheduled",
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1.0f),
                    style = MaterialTheme.typography.h6, 
                    fontWeight = FontWeight.W700, 
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }

        ButtonRow(
            onDetailsClicked = { onViewDetailsClicked() },
            onDoneClicked = { onDoneClicked() }
        )
    }
}

@Composable
fun ButtonRow(onDetailsClicked: () -> Unit, onDoneClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { onDetailsClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = viewPort),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = customCyan,
                contentColor = Color.White
            )
        ) {
            Text(text = "VIEW DETAILS", style = MaterialTheme.typography.body1,fontWeight = FontWeight.W700, color = Color.White, modifier = Modifier.padding(horizontal = viewPort))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onDoneClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = viewPort),
            border = BorderStroke(1.dp, customCyan),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = customCyan
            )
        ) {
            Text(text = "DONE", style = MaterialTheme.typography.body1,fontWeight = FontWeight.W700, color = customCyan, modifier = Modifier.padding(horizontal = viewPort))
        }
    }
}