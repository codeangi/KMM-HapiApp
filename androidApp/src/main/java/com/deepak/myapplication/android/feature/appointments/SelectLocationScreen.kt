package com.deepak.myapplication.android.feature.appointments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.CareTeamData
import com.deepak.myapplication.model.LocationData


@Composable
fun SelectLocationScreen(selectedCareTeam: CareTeamData?, onClickOfLocation: (LocationData) -> Unit) {
    Column(modifier = Modifier.padding(viewPort)) {
        Text(
            text = "Where would you like to be seen?",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.W600,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(Modifier.weight(1f)) {
            item {
                LocationCardUi(
                    LocationData(
                    selectedCareTeam?.hospitalLocation,
                        selectedCareTeam?.locationAddress,
                    "Attending hours:\n8AM - 2PM - Mon,Tue,Wed,Thur,Fri"
                ), onClickOfLocation)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocationCardUi(locationData: LocationData, onClickOfLocation: (LocationData) -> Unit) {
    Card(
        onClick = { onClickOfLocation(locationData) },
        border = BorderStroke(1.dp, lightGrey)
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = viewPort)
            ) {
                Image(
                    painter = painterResource(R.drawable.clinic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(10))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = locationData?.name ?: "",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W700,
                        color = Color.Black
                    )
                    Text(
                        text = locationData?.address ?: "",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray.copy(0.8f),
                        maxLines = 2
                    )
                    Text(
                        text = locationData?.attendingHours ?: "",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray.copy(0.8f),
                        maxLines = 2
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}