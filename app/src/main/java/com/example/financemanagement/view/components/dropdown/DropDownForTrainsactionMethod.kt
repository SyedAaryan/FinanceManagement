package com.example.financemanagement.view.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.financemanagement.repository.TransactionRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownForTransactionMethod(

) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ){
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Method") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(color = Color.White)
            ){
                TransactionRepository.TransactionMethod.entries.forEach { method ->
                    DropdownMenuItem(
                        text = { Text(method.toString()) },
                        onClick = {
                            selectedItem = method.toString()
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}