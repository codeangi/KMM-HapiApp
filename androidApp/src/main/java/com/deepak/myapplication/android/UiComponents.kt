package com.deepak.myapplication.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBarOnlyBackButton(onBack:()->Unit){
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null, modifier = Modifier.clickable {
                onBack.invoke()
            })

        }
    }
}
val viewPortTop= 20.dp

@Composable
fun AppBarWithTitle( title:String){
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
            Text(text = title, style = MaterialTheme.typography.h5)

        }
    }
}