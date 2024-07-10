package com.example.financemanagement.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.TransactionFloatingButton

@Composable
fun TransactionView(
    navController: NavController
) {
    Scaffold (
        topBar = {
            AppBar(title = "Transactions",navController = navController)
        },
        floatingActionButton = {
            TransactionFloatingButton(title = "Add Transaction") {
                navController.navigate("addTransactionScreen")
            }
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