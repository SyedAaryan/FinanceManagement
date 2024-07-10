package com.example.financemanagement.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.financemanagement.R


@Composable
fun TransactionFloatingButton(
    title: String,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier.padding(all = 20.dp),
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.AppLightBlue),
        onClick = {
            onClick()
        }
    ) {
        Text(text = title)
    }
}