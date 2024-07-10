package com.example.financemanagement.services

sealed class Screens (val route: String){
    data object HomeScreen: Screens("homeScreen")
    data object LoginScreen: Screens("loginScreen")
    data object SignUpScreen : Screens("signUpScreen")
    data object SettingScreen: Screens("settingScreen")
    data object TransactionScreen: Screens("transactionScreen")
    data object AddTransactionScreen: Screens("addTransactionScreen")
}