package com.example.financemanagement.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.dropdown.DropDownForHistory
import com.example.financemanagement.view.components.dropdown.DropDownForTransactionMethod
import com.example.financemanagement.view.components.dropdown.Timeline
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
            DropDownForHistory(
                onSelectionChange = { viewmodel.onTimeLineChange(it) }
            )

            Spacer(modifier =Modifier.padding(8.dp))

            when(viewmodel.selectedTimeLine.value){
                "Select Date" -> Button(onClick = { /*TODO*/ }) {
                    Text(text = "Select Date")
                }
            }

            Spacer(modifier =Modifier.padding(8.dp))

            DropDownForTransactionMethod(
                viewTitle = "History",
                onSelectionChange = { viewmodel.onPaymentMethodChange(it) }
            )
        }
    }
}