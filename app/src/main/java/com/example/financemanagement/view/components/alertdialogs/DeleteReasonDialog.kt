package com.example.financemanagement.view.components.alertdialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.financemanagement.view.components.dropdown.DropDownForReason

@Composable
fun DeleteReasonDialog(
    reasonsMap: Map<String, String>,
    selectedReasonKey: MutableState<String>,
    onDeleteReason: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                onDeleteReason()
                onDismissRequest()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                Text(text = "Select a reason to delete")
                DropDownForReason(
                    reasonsMap = reasonsMap,
                    selectedReasonKey = selectedReasonKey
                )
            }
        }
    )
}