package com.example.financemanagement.services

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financemanagement.view.HomeView

@Composable
fun Navigation(){
    NavHost(
        navController = rememberNavController(), startDestination = Screens.HomeScreen.route){
            composable(Screens.HomeScreen.route){
                HomeView()
            }
       }
}