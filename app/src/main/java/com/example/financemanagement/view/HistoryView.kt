package com.example.financemanagement.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.DatePicker
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
                Timeline.DATE.toString() ->{
                    OutlinedButton(
                        onClick = {
                            showDatePicker.value = true
                        }
                    ) {
                        Text(viewmodel.historyByDate?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()).toLocalDate().toString()} ?: "Select Date" )
                    }
                    DatePicker(
                        openDialog = showDatePicker,
                        onDateSelected = { selectedDate ->
                            viewmodel.onHistoryByDateChange(selectedDate)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            DropDownForTransactionMethod(
                viewTitle = "History",
                onSelectionChange = { viewmodel.onPaymentMethodChange(it) }
            )
        }
    }
}
