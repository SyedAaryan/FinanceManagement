package com.example.financemanagement.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.BottomBar
import com.example.financemanagement.view.components.HomeViewCard
import com.example.financemanagement.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeView(navController: NavController, viewModel: LoginViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppBar(title = "Finance Management")
        },
        bottomBar = { BottomBar() }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeViewCard()
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.signOut()
                        navController.navigate("loginScreen")
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Sign Out")
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    // Replace with an appropriate preview setup if needed
    // HomeView(navController = ..., viewModel = ...)
}
