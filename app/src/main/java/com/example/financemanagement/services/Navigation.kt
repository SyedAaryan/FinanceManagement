package com.example.financemanagement.services

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financemanagement.view.HomeView
import com.example.financemanagement.view.LoginView
import com.example.financemanagement.view.SettingsView
import com.example.financemanagement.view.SignUpVIew
import com.example.financemanagement.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel = LoginViewModel()

    val startDestination = if (loginViewModel.user != null) {
        Screens.HomeScreen.route
    } else {
        Screens.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.HomeScreen.route) {
            HomeView(navController, loginViewModel)
        }

        composable(Screens.LoginScreen.route) {
            LoginView(loginViewModel, navController)
        }

        composable(Screens.SignUpScreen.route) {
            SignUpVIew(loginViewModel, navController)
        }

        composable(Screens.SettingScreen.route){
            SettingsView(navController)
        }
    }
}
