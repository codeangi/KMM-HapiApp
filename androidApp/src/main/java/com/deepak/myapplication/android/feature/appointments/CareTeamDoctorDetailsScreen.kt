package com.deepak.myapplication.android.feature.appointments

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.deepak.myapplication.android.MainActivityViewModel
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.CareTeamData
import org.koin.androidx.compose.koinViewModel

@Composable
fun CareTeamDoctorDetailsScreen(onBack : () -> Unit) {
    val mainActivityViewModel: MainActivityViewModel = koinViewModel()
    val selectedCareDoctor = mainActivityViewModel.selectedCareTeamDoctor
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBack.invoke()
                    }
                    .zIndex(1f)
                    .padding(viewPort)
            )
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painter = painterResource(id = R.drawable.clinic),
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    modifier = Modifier
                        .size(84.dp, 64.dp)
                        .padding(start = viewPort)
                        .background(customCyan, RoundedCornerShape(100))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_person_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = selectedCareDoctor?.doctorName ?: "", style = MaterialTheme.typography.h5, fontWeight = FontWeight.W700, color = Color.Black, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Accepting", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = customCyan, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Specialities", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = Color.Black, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = selectedCareDoctor?.designation ?: "", style = MaterialTheme.typography.body1, color = Color.Black, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "About", style = MaterialTheme.typography.body1, color = Color.Black, fontWeight = FontWeight.W700, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = selectedCareDoctor?.doctorDescription ?: "", style = MaterialTheme.typography.body1, color = Color.Black, modifier = Modifier.padding(horizontal = viewPort))

                Spacer(modifier = Modifier.height(16.dp))

                GeneralInformationSectionUi(selectedCareDoctor)

                Spacer(modifier = Modifier.height(12.dp))

                Divider(Modifier.height(1.dp), color = lightGrey)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Locations", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = Color.Black, modifier = Modifier.padding(horizontal = viewPort))

                LocationDetailsCardUi(selectedCareDoctor)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = viewPort)
                ) {
                    Text(text = "SCHEDULE APPOINTMENT", style = MaterialTheme.typography.body1,fontWeight = FontWeight.W700, color = Color.White, modifier = Modifier.padding(horizontal = viewPort))
                }

                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}

@Composable
fun LocationDetailsCardUi(selectedCareDoctor: CareTeamData?) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = viewPort)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(customCyan, RoundedCornerShape(10))
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_home_work_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(text = selectedCareDoctor?.hospitalLocation ?: "", style = MaterialTheme.typography.body1, fontWeight = FontWeight.W700, color = Color.Black)
                Text(text = selectedCareDoctor?.locationAddress ?: "", style = MaterialTheme.typography.body1, color = Color.Gray.copy(0.8f), maxLines = 2)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            Modifier
                .height(1.dp)
                .padding(horizontal = viewPort), color = lightGrey)
    }
}

@Composable
fun GeneralInformationSectionUi(selectedCareDoctor: CareTeamData?) {
    Row(modifier = Modifier.padding(horizontal = viewPort)) {
       Column(Modifier.weight(1f)) {
           CategoryWithData("Gender", selectedCareDoctor?.gender ?: "")
           CategoryWithData("Years in practice", selectedCareDoctor?.yearsOfPractice ?: "")
           CategoryWithData("Group affiliations", selectedCareDoctor?.groupAffiliations ?: "", true)
           CategoryWithData("Medical school", selectedCareDoctor?.medicalSchool ?: "")
       }
        Column(Modifier.weight(1f)) {
            CategoryWithData("Languages", selectedCareDoctor?.languages ?: "")
            CategoryWithData("Board Certification", selectedCareDoctor?.boardCertification ?: "")
            CategoryWithData("Hospital affiliations", selectedCareDoctor?.hospitalAffiliations ?: "", true)
            CategoryWithData("Residency", selectedCareDoctor?.residency ?: "")
        }
    }
}

@Composable
fun CategoryWithData(category: String, data: String, isHighlighted: Boolean = false) {
    Text(text = category, style = MaterialTheme.typography.body1, fontWeight = FontWeight.W600, color = Color.Gray.copy(0.8f) )

    Spacer(modifier = Modifier.height(12.dp))

    Text(text = data, style = MaterialTheme.typography.body1, fontWeight = if (isHighlighted) FontWeight.W600 else FontWeight.W400, color = if (isHighlighted) customCyan else Color.Black )

    Spacer(modifier = Modifier.height(16.dp))
}
