package com.example.financemanagement.view

import SettingsViewDialog
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.R
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.SettingsViewModel

@Composable
fun SettingsView(
    navController: NavController,
    viewmodel: SettingsViewModel = viewModel()
) {

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                title = "Settings",
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            InputTextField(
                label = "Salary",
                value = viewmodel.salary,
                onValueChanged = {
                    viewmodel.salary = it
                }

            )

            Button(
                modifier = Modifier.padding(it),
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
                modifier = Modifier.padding(it),
                onClick = {
                    showDialog = true
                }) {
                Text(text = "Add Reason", style = TextStyle(fontSize = 18.sp))
            }

            if(showDialog){
                SettingsViewDialog(
                    onDismissRequest = {
                        showDialog = false
                    }
                )
            }

        }

    }
}

