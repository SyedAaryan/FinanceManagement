package com.example.financemanagement.view.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.DatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    openDialog: MutableState<Boolean>,
    onDateSelected: (Long?) -> Unit // Callback to pass selected date to ViewModel
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDateSelected(datePickerState.selectedDateMillis) // Pass the date to the callback
                    },
                    enabled = confirmEnabled
                ) {
                    Text(text = "Okay")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}