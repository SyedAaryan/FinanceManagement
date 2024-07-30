package com.example.financemanagement.view

import com.example.financemanagement.view.components.SettingsViewDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.R
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.DropDownForReason
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.SettingsViewModel

@Composable
fun SettingsView(
    navController: NavController,
    viewmodel: SettingsViewModel = viewModel()
) {

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppBar(
                title = "Settings",
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            InputTextField(
                label = "Salary",
                value = viewmodel.salary,
                onValueChanged = {
                    viewmodel.salary = it
                }
            )

            Button(
                onClick = {
                    viewmodel.addSalary(
                        onSuccess = {

                        },
                        onFailure = {

                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.AppLightGreen)
                )
            ) {
                Text(text = "Add Salary", style = TextStyle(fontSize = 18.sp))
            }

            Button(
                onClick = {
                    dialogTitle = "Add Reason"
                    showDialog = true
                }) {
                Text(text = "Add Reason", style = TextStyle(fontSize = 18.sp))
            }

            Button(
                onClick = {
                    dialogTitle = "Delete Reason"
                    showDialog = true
                }) {
                Text(text = "Delete Reason", style = TextStyle(fontSize = 18.sp))
            }

            Button(
                onClick = {
                    dialogTitle = "Update Reason"
                    showDialog = true
                }) {
                Text(text = "Update Reason", style = TextStyle(fontSize = 18.sp))
            }

            DropDownForReason(
                reasonsMap = viewmodel.reasonsMap,
                selectedReasonKey = viewmodel.selectedReasonKey
            )

            if (showDialog) {
                SettingsViewDialog(
                    title = dialogTitle,
                    onDismissRequest = {
                        showDialog = false
                    }
                )
            }
        }
    }
}

