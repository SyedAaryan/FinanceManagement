package com.example.financemanagement.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar

@Composable
fun AddTransactionView(
    navController: NavController
) {
    Scaffold (
        topBar = {
            AppBar(title = "Add Transaction",navController = navController)
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){

        }
    }
}