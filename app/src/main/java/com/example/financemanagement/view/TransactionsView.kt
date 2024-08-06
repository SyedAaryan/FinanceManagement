package com.example.financemanagement.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.cards.TransactionCard
import com.example.financemanagement.view.components.TransactionFloatingButton
import com.example.financemanagement.viewmodel.TransactionsViewModel

@Composable
fun TransactionView(
    navController: NavController,
    viewmodel: TransactionsViewModel = viewModel()
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
            items(viewmodel.transactionMap.entries.toList()){ transaction->
                TransactionCard(transaction = transaction.value, reason = viewmodel.reasonsMap[transaction.value.reason])
            }
        }
    }
}