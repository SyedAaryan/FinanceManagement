package com.example.financemanagement.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.BottomBar
import com.example.financemanagement.view.components.HomeViewCard

@Composable
fun SettingsView(navController: NavController){
    Scaffold(
        topBar = {
            AppBar(
                title = "Settings",
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeViewCard()

        }
    }
}