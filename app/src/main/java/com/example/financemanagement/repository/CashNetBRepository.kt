package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap

object CashNetBRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    //This function fetches the total netBanking transactions of the month
    suspend fun getTotalNetBTransactions(): Double {
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            try {
                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                val snapshot = database.getReference("Users/$uid/Transactions")
                    .get()
                    .await()

                var totalAmount = 0.0

                for (transaction in snapshot.children) {
                    val method = transaction.child("method").getValue(String::class.java)
                    val dateEpoch = transaction.child("date").getValue(Long::class.java)
                    val amount = transaction.child("amount").getValue(Double::class.java)

                    if (method == "Net Banking" && dateEpoch != null && amount != null) {
                        val transactionDate = Calendar.getInstance().apply {
                            timeInMillis = dateEpoch
                        }
                        val transactionMonth = transactionDate.get(Calendar.MONTH)
                        val transactionYear = transactionDate.get(Calendar.YEAR)

                        if (transactionMonth == currentMonth && transactionYear == currentYear) {
                            totalAmount += amount
                        }
                    }
                }

                return totalAmount
            } catch (e: Exception) {
                throw e
            }

        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }


//    suspend fun getNetBTransactions(): Map<String, Transactions> {
//        val user = FirebaseService.user
//        if (user != null) {
//            val uid = user.uid
//            try {
//                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
//                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
//
//                val snapshot = database.getReference("Users/$uid/Transactions")
//                    .get()
//                    .await()
//
//                val netBankingTransactions = HashMap<String, Transactions>()
//
//                for (transaction in snapshot.children) {
//                    val method = transaction.child("method").getValue(String::class.java)
//                    val dateEpoch = transaction.child("date").getValue(Long::class.java)
//                    val transactionId = transaction.key
//                    if (method == "Net Banking" && dateEpoch != null && transactionId != null) {
//                        val transactionDate = Calendar.getInstance().apply {
//                            timeInMillis = dateEpoch
//                        }
//                        val transactionMonth = transactionDate.get(Calendar.MONTH)
//                        val transactionYear = transactionDate.get(Calendar.YEAR)
//
//                        if (transactionMonth == currentMonth && transactionYear == currentYear) {
//                            val transactionData = transaction.getValue(Transactions::class.java)
//                            if (transactionData != null) {
//                                netBankingTransactions[transactionId] = transactionData
//                            }
//                        }
//                    }
//                }
//
//                return netBankingTransactions
//            } catch (e: Exception) {
//                throw e
//            }
//
//        } else {
//            throw IllegalStateException("User is not authenticated.")
//        }
//    }
}


//This repository fetches the cash and netBanking transactions of the month