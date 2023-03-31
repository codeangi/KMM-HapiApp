package com.deepak.myapplication.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey

@Composable
fun AppBarOnlyBackButton(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onBack.invoke()
                })

        }
    }
}

val viewPortTop = 20.dp
val viewPort = 20.dp

@Composable
fun AppBarWithTitle(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_home_24), contentDescription = "Hapi Care Icon",
                Modifier
                    .size(40.dp, 40.dp)
                    .align(CenterVertically),
                tint = customCyan)
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(text = title, style = MaterialTheme.typography.h4, fontWeight = FontWeight.W700, color = customCyan, modifier = Modifier.weight(1f))

            Icon(painter = painterResource(id = R.drawable.round_call_24), contentDescription = "Call",
                Modifier
                    .size(32.dp, 32.dp)
                    .align(CenterVertically),
                tint = customCyan)

        }
        Spacer(modifier = Modifier.height(12.dp))

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(1.dp),
            color = lightGrey)
    }
}