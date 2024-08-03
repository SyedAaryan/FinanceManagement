package com.example.financemanagement.view.components.alertdialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.financemanagement.view.components.InputTextField

@Composable
fun AddReasonDialog(
    reason: String,
    onReasonChanged: (String) -> Unit,
    onAddReason: () -> Unit,
    onDismissRequest: () -> Unit
){

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                onAddReason()
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
                InputTextField(
                    label = "Add Reason",
                    value = reason,
                    onValueChanged = {
                    onReasonChanged(it)
                    }
                )
            }
        }
    )


}