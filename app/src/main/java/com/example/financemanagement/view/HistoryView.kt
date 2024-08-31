package com.example.financemanagement.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.cards.TransactionCard
import com.example.financemanagement.view.components.TransactionFloatingButton
import com.example.financemanagement.view.components.dropdown.DropDownForHistory
import com.example.financemanagement.view.components.dropdown.DropDownForTransactionMethod
import com.example.financemanagement.viewmodel.HistoryViewModel

@Composable
fun HistoryView(
    navController: NavController,
    viewmodel: HistoryViewModel = viewModel()
) {
    Scaffold (
        topBar = {
            AppBar(title = "History",navController = navController)
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DropDownForHistory()

            Spacer(modifier =Modifier.padding(8.dp))

            DropDownForTransactionMethod()
        }
    }
}