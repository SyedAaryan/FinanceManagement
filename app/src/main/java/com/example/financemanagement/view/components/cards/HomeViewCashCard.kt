package com.example.financemanagement.view.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanagement.R

@Composable
fun HomeViewCashCard (
    totalCash: Int
){

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.AppLightGreen))
    ){
        Column (modifier = Modifier.padding(60.dp).fillMaxWidth()){
            Text(text = "Cash Spent This Month",fontSize = 20.sp)
            Text(text = totalCash.toString())
        }
    }

}

//This card fetches the cash transactions of this month displays the total value
//It is clickable and the view shows the list of cash transactions of this month