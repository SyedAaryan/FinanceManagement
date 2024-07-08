package com.example.financemanagement.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financemanagement.R
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.SalaryViewModel

@Composable
fun SettingsView(navController: NavController, viewModel: SalaryViewModel){
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
                value = viewModel.salary,
                onValueChanged = {
                    viewModel.onSalaryChange(it)
                }

            )

            Button(
                modifier = Modifier.padding(it),
                onClick = {
                    viewModel.addSalary(
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
                Text(text = "Add Salary", style = TextStyle(fontSize = 18.sp))
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SettingsViewPreview() {
    val navController = rememberNavController()
    val mockViewModel = SalaryViewModel()

    SettingsView(navController = navController, viewModel = mockViewModel)
}
