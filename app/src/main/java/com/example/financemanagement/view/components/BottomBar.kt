package com.example.financemanagement.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.financemanagement.R

@Composable
fun BottomBar(){
    BottomAppBar(
        containerColor = colorResource(id = R.color.AppLightBlue) ,
        contentColor = Color.White,
    ) {
        Spacer(modifier = Modifier.weight(1f, true))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.AppLightGreen)),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text("Transaction", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(30.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.AppLightGreen)),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text("Pie Chart", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f, true))
    }
}