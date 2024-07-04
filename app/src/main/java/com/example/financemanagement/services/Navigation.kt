package com.example.financemanagement.services

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financemanagement.view.HomeView
import com.example.financemanagement.view.LoginView
import com.example.financemanagement.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel = LoginViewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeView(navController, loginViewModel)
        }

        composable(Screens.LoginScreen.route) {
            LoginView(loginViewModel, navController)
        }
    }
}
