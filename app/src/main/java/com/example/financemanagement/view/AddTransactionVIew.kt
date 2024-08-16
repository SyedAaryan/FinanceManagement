package com.example.financemanagement.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.DatePicker
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.AddTransactionViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import com.example.financemanagement.R
import com.example.financemanagement.repository.TransactionRepository
import com.example.financemanagement.view.components.DropDownForReason
import com.example.financemanagement.view.components.RadioButtonGroup

@Composable
fun AddTransactionView(
    navController: NavController,
    viewmodel: AddTransactionViewModel = viewModel()
) {

    val showDatePicker = remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            AppBar(title = "Add Transaction",navController = navController)
        }
    ){
        Column (modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    showDatePicker.value = true
                }
            ) {
                Text(viewmodel.transactionDate?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()).toLocalDate().toString()} ?: "Select Date" )
            }

            DatePicker(
                openDialog = showDatePicker,
                onDateSelected = { selectedDate ->
                    viewmodel.onTransactionDateChange(selectedDate)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            InputTextField(
                label = "Amount",
                value = viewmodel.transactionAmount,
                onValueChanged = {
                    viewmodel.onTransactionAmountChange(it)
                }
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            DropDownForReason(
                reasonsMap = viewmodel.reasonsMap,
                selectedReasonKey = viewmodel.selectedReason
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Payment Method", style = TextStyle(fontSize = 16.sp))

            RadioButtonGroup(
                options = listOf(
                    TransactionRepository.TransactionMethod.Cash.toString(),
                    TransactionRepository.TransactionMethod.NetBanking.toString()),
                selectedOption = viewmodel.selectedPaymentMethod,
                onOptionSelected = {
                    viewmodel.onPaymentMethodChange(it)
                }
            )

            Button(
                onClick = {
                    if (viewmodel.transactionDate == null || viewmodel.selectedReason.value.isEmpty() || viewmodel.transactionAmount.isEmpty() || viewmodel.selectedPaymentMethod.isEmpty()){
                        return@Button
                    }
                    viewmodel.addTransaction(
                        onSuccess = {
                            navController.navigateUp()
                        },
                        onFailure = {

                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.AppLightGreen)
                )
            ) {
                Text(text = "Add Transaction", style = TextStyle(fontSize = 18.sp))
            }
        }
    }
}