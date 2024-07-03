package com.example.financemanagement.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.financemanagement.view.components.AppBar
import com.example.financemanagement.view.components.BottomBar
import com.example.financemanagement.view.components.HomeViewCard

@Composable
fun HomeView (){
    Scaffold (
        topBar = {
            AppBar(title = "Finance Management")
        },

        bottomBar = { BottomBar()}
    ){
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            HomeViewCard()
        }
    }

}

@Preview
@Composable
fun HomeViewPreview(){
    HomeView()
}