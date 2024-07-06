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
import androidx.navigation.NavController
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.BottomBar
import com.example.financemanagement.view.components.HomeViewCard
import com.example.financemanagement.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeView(navController: NavController, viewModel: LoginViewModel) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = { Text("sign Out") },
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                viewModel.signOut()
                                navController.navigate("loginScreen")
                            }

                        })
                }
            }
        }
    ){
    Scaffold(
        topBar = {
            AppBar(
                title = "Finance Management",
                drawerState = drawerState,
                coroutineScope = coroutineScope)
        },
        bottomBar = { BottomBar() }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeViewCard()

        }
    }
    }
}


