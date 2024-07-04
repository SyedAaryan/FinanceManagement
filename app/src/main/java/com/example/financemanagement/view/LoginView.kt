package com.example.financemanagement.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            InputTextField(
                label = "Username",
                value = viewModel.username,
                onValueChanged = {
                    viewModel.onUsernameChange(it)
                }
            )

            InputTextField(
                label = "Password",
                value = viewModel.password,
                onValueChanged = {
                    viewModel.onPasswordChange(it)
                }
            )

            Button(
                onClick = {
                        viewModel.signIn(
                            onSuccess = {
                                Log.d("LoginView", "Login successful")
                                navController.navigate("homeScreen")
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                }
            ) {
                Text("Login")
            }
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
   LoginView(viewModel = LoginViewModel(), navController = NavController(LocalContext.current))
}