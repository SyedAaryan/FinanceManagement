package com.example.financemanagement.model

data class Transactions(
    val date: Long = 0L,
    val reason: String = "",
    val amount: Int = 0,
    val method: String = ""
)