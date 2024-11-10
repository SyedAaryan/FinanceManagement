package com.example.financemanagement.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.BottomBar
import com.example.financemanagement.view.components.cards.HomeViewCard
import com.example.financemanagement.view.components.cards.HomeViewCashCard
import com.example.financemanagement.view.components.cards.HomeViewNetBCard
import com.example.financemanagement.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    navController: NavController,
    viewmodel: HomeViewModel = viewModel()
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate("settingScreen")
                            }

                        }
                    )

                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = { Text("Sign Out") },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                viewmodel.signOut()
                                navController.navigate("loginScreen")
                            }

                        }
                    )
                }
            }
        }
    ){
        Scaffold(
            topBar = {
                AppBar(
                    title = "Finance Management",
                    drawerState = drawerState,
                    coroutineScope = coroutineScope,
                    navController = navController)
            },
            bottomBar = { BottomBar(navController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    HomeViewCard(viewmodel.salary)

                    Spacer(modifier = Modifier.height(20.dp))

                    HomeViewCashCard(viewmodel.totalCashTransactions)

                    Spacer(modifier = Modifier.height(20.dp))

                    HomeViewNetBCard(viewmodel.totalNetBTransactions)
                }
            }
        }
    }
}

