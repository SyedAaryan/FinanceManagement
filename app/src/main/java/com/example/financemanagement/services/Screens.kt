package com.example.financemanagement.services

sealed class Screens (val route: String){
    data object HomeScreen: Screens("homeScreen")
    data object LoginScreen: Screens("loginScreen")
}