package com.deepak.myapplication.android.feature.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.AppBarWithTitle
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.viewPort
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewModel: HomeViewModel = koinViewModel()
    val viewModelState by viewModel.homeUiState.collectAsState()
    LaunchedEffect(key1 = lifecycle, block = {
        viewModel.getPatientDetails()
    })
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBarWithTitle(title = "Hapi Care")
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(modifier = Modifier
                .padding(horizontal = viewPort)
                .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Hi, ${viewModelState.patientName?:""}", style = MaterialTheme.typography.h4, fontWeight = FontWeight.W700, color = Color.Black)

                Text(text = "Monday, March 13", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(20.dp))

                FeaturePills()

                Spacer(modifier = Modifier.height(8.dp))

                BookNowCard()

                Spacer(modifier = Modifier.height(16.dp))

                MyDoctorSectionUi(viewModel)

                Spacer(modifier = Modifier.height(20.dp))

                ClinicsSectionUi(viewModel)

                Spacer(modifier = Modifier.height(20.dp))

                MedicalRecordsSectionUi()


            }
        }
    }
}

@Composable
fun MedicalRecordsSectionUi() {
    Column(horizontalAlignment = CenterHorizontally) {
        Text(text = "Medical Records", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            MedicalRecordCardUi("Immunizations")
            MedicalRecordCardUi("Test Results")
            MedicalRecordCardUi("My Conditions")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { },
            Modifier.padding(end = 12.dp),
            border = BorderStroke(2.dp, Color.Blue),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
        ){
            Text( text = "VIEW ALL RECORDS", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W500, color = Color.Blue )
        }
    }
}

@Composable
fun MedicalRecordCardUi(categories: String) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_records_24), contentDescription = null,
                Modifier
                    .size(24.dp, 24.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Blue)

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = categories, style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = Color.Blue, modifier = Modifier.weight(1f))

            Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = null,
                Modifier
                    .size(24.dp, 24.dp)
                    .align(Alignment.CenterVertically),
                tint = Color.Blue)

        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(Modifier.height(1.dp), color = Color.Gray.copy(0.4f))
    }
}

@Composable
fun ClinicsSectionUi(viewModel: HomeViewModel) {
    Column {
        Text(text = "Clinics", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(viewModel.clinicDataUiState.value) {
                ClinicsCardUi(it.clinicName ?: "", it.address ?: "")
            }
        }
    }
}

@Composable
fun ClinicsCardUi(name: String, address: String) {
    Box(
        modifier = Modifier
            .size(220.dp, 180.dp)
            .padding(end = 16.dp)
            .background(Color.Gray.copy(0.5f), shape = RoundedCornerShape(24.dp)),
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = name,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.W700,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = address, style = MaterialTheme.typography.body2)
                }
            }
        }

    }
}

@Composable
fun MyDoctorSectionUi(viewModel: HomeViewModel) {
    Column {
        Text(text = "My Doctors", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(viewModel.doctorDataUiState.value) {
                DoctorCardUi(it.doctorName ?: "", it.designation ?: "")
            }
        }
    }
}

@Composable
fun DoctorCardUi(name: String, designation: String) {
    Column(Modifier.padding(end = 12.dp)) {
        Box(
            modifier = Modifier
                .size(140.dp, 180.dp)
                .background(Color.Blue.copy(0.5f), shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(64.dp, 64.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = name, style = MaterialTheme.typography.body2, fontWeight = FontWeight.W700, color = Color.Black)

        Text(text = designation, style = MaterialTheme.typography.body2)
    }
}


@Composable
fun BookNowCard() {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.Blue.copy(0.5f), shape = RoundedCornerShape(24.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
               painter = painterResource(id = R.drawable.baseline_add_home_work_24),
               contentDescription = null,
               tint = Color.White,
               modifier = Modifier
                   .size(48.dp, 48.dp)
           )

            Spacer(modifier = Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Book Now", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.White)
                Text(text = "Get the next available appointment, now", style = MaterialTheme.typography.h6, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_right_alt_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp, 32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun FeaturePills() {
    LazyRow {
        item {
            FeaturePillUi(featureName = "Medical Records")
            FeaturePillUi(featureName = "Insurance Card")
            FeaturePillUi(featureName = "Urgent Care")
        }
    }
}

@Composable
fun FeaturePillUi(featureName: String) {
    OutlinedButton(
        onClick = { },
        Modifier.padding(end = 12.dp),
        border = BorderStroke(1.dp, Color.Blue),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
    ){
        Text( text = featureName, style = MaterialTheme.typography.body1, fontWeight = FontWeight.W600, color = Color.Blue )
    }
}
