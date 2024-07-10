package com.example.financemanagement.services

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financemanagement.view.AddTransactionView
import com.example.financemanagement.view.HomeView
import com.example.financemanagement.view.LoginView
import com.example.financemanagement.view.SettingsView
import com.example.financemanagement.view.SignUpVIew
import com.example.financemanagement.view.TransactionView


@Composable
fun Navigation() {
    val navController = rememberNavController()

    val startDestination = if (FirebaseService.user != null) {
        Screens.HomeScreen.route
    } else {
        Screens.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.HomeScreen.route) {
                HomeView(navController)
        }

        composable(Screens.LoginScreen.route) {
            LoginView(navController)
        }

        composable(Screens.SignUpScreen.route) {
            SignUpVIew(navController)
        }

        composable(Screens.SettingScreen.route){
                SettingsView(navController)
        }

        composable(Screens.AddTransactionScreen.route){
            AddTransactionView(navController)
        }

        composable(Screens.TransactionScreen.route){
            TransactionView(navController)
        }
    }
}
