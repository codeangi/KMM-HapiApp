package com.deepak.myapplication.android.feature.appointments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort


@Composable
fun SelectReasonScreen(listOfReasons: List<String>, onClickOfReason: (String) -> Unit) {
    Column(modifier = Modifier.padding(viewPort)) {
        Text(
            text = "What is the reason for your visit?",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.W600,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(listOfReasons) {
                VisitReasonCardUi(it, onClickOfReason)
            }
        }
    }
}

@Composable
fun VisitReasonCardUi(reason: String, onClickOfReason: (String) -> Unit) {
    OutlinedButton(
        onClick = { onClickOfReason(reason) },
        Modifier
            .padding(end = 12.dp)
            .heightIn(min = 48.dp),
        border = BorderStroke(1.dp, lightGrey),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text( text = reason, style = MaterialTheme.typography.body1, fontWeight = FontWeight.W600, color = Color.Black, modifier = Modifier.weight(1f))
        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = null,
            Modifier
                .size(18.dp, 18.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Gray.copy(0.8f)
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}
