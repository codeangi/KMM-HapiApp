package com.deepak.myapplication.android.feature.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.AppBarOnlyBackButton
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.CareTeamData
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppointmentCareTeamScreen(onBack: () -> Boolean, onDoctorCardClick: (CareTeamData) -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewModel: AppointmentViewModel = koinViewModel()
    LaunchedEffect(key1 = lifecycle, block = {
        viewModel.getMyCareTeamData()
    })
    Scaffold(modifier = Modifier.fillMaxSize(),
    topBar = {
        AppBarOnlyBackButton {
            onBack()
        }
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(Modifier.padding(viewPort)) {
                Text(text = "Who would you like to see?", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "My care team", style = MaterialTheme.typography.h6, fontWeight = FontWeight.W700, color = Color.Black)

                LazyColumn {
                    items(viewModel.careTeamDataState.value) {
                        CareTeamDoctorCardUi(it) { careTeamData ->
                            onDoctorCardClick(careTeamData)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CareTeamDoctorCardUi(careTeamData: CareTeamData, onDoctorCardClick: (CareTeamData) -> Unit) {
    Column(Modifier.clickable { onDoctorCardClick(careTeamData) }) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(customCyan, RoundedCornerShape(100))
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_person_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                    tint = White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(text = careTeamData.doctorName ?: "", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = Black)
                Text(text = careTeamData.designation ?: "", style = MaterialTheme.typography.body1, color = Gray.copy(0.8f))
                Text(text = careTeamData.hospitalLocation ?: "", style = MaterialTheme.typography.body1, color = Gray.copy(0.8f))
            }

            Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = null,
                Modifier
                    .size(24.dp, 24.dp)
                    .align(Alignment.CenterVertically),
                tint = Gray.copy(0.8f)
            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(Modifier.height(1.dp), color = lightGrey)
    }
}
