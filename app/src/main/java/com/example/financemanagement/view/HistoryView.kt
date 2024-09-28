package com.example.financemanagement.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.DatePicker
import com.example.financemanagement.view.components.cards.TransactionCard
import com.example.financemanagement.view.components.dropdown.DropDownForHistory
import com.example.financemanagement.view.components.dropdown.DropDownForTransactionMethod
import com.example.financemanagement.view.components.dropdown.Timeline
import com.example.financemanagement.viewmodel.HistoryViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun HistoryView(
    navController: NavController,
    viewmodel: HistoryViewModel = viewModel()
) {

    val showDatePicker = remember { mutableStateOf(false) }


    val transactions by viewmodel.transactionsData

    Scaffold(
        topBar = {
            AppBar(title = "History", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DropDownForHistory(
                onSelectionChange = { viewmodel.onTimeLineChange(it) }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            when (viewmodel.selectedTimeLine.value) {
                Timeline.DATE.toString() -> {
                    OutlinedButton(
                        onClick = {
                            showDatePicker.value = true
                        }
                    ) {
                        Text(viewmodel.historyByDate?.let {
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(
                                    it
                                ), ZoneId.systemDefault()
                            ).toLocalDate().toString()
                        } ?: "Select Date")
                    }
                    DatePicker(
                        openDialog = showDatePicker,
                        onDateSelected = { selectedDate ->
                            viewmodel.onHistoryByDateChange(selectedDate)
                        }
                    )

                    DropDownForTransactionMethod(
                        viewTitle = "History",
                        onSelectionChange = { viewmodel.onPaymentMethodChange(it) }
                    )

                    Button(onClick = {
                        viewmodel.performAction(Timeline.DATE.toString())
                    }) {
                        Text(text = "Search")
                    }
                }

                Timeline.PREVIOUS_WEEK.toString() -> {
                    DropDownForTransactionMethod(
                        viewTitle = "History",
                        onSelectionChange = { viewmodel.onPaymentMethodChange(it) }
                    )

                    Button(onClick = {
                        viewmodel.performAction(Timeline.PREVIOUS_WEEK.toString())
                    }) {
                        Text(text = "Search")
                    }
                }

                Timeline.PREVIOUS_30_DAYS.toString() -> {
                    DropDownForTransactionMethod(
                        viewTitle = "History",
                        onSelectionChange = { viewmodel.onPaymentMethodChange(it) }
                    )

                    Button(onClick = {
                        viewmodel.performAction(Timeline.PREVIOUS_30_DAYS.toString())
                    }) {
                        Text(text = "Search")
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ){
                items(transactions.values.toList()){ transaction->
                    TransactionCard(transaction = transaction, reason = viewmodel.reasonsMap[transaction.reason])
                }
            }

        }
    }
}
