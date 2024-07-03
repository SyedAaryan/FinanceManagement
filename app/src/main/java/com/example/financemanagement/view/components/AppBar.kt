package com.example.financemanagement.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.financemanagement.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String
){
    TopAppBar(
        title = { Text(text = title)},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.AppLightBlue))
    )
}