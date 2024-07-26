package com.example.financemanagement.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DropDownForReason() {

    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select Time Period") }



}