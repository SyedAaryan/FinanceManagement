package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService
import kotlinx.coroutines.tasks.await

object HistoryRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    suspend fun fetchHistoryByDate(selectedDate: Long, selectedMethod: String): Result<Map<String?, Transactions>> {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        val historyRef = database.getReference("Users/$uid/Transactions")

        return try {
            val snapshot = historyRef.get().await()
            val transactionMap = snapshot.children
                .mapNotNull { dataSnapshot ->
                    val transactions = dataSnapshot.getValue(Transactions::class.java)
                    val transactionDate = transactions?.date ?: return@mapNotNull null

                    val methodCheck = if (selectedMethod == TransactionRepository.TransactionMethod.BOTH.toString()) {
                        true
                    } else {
                        transactions.method == selectedMethod
                    }

                    if (transactionDate == selectedDate && methodCheck) {
                        dataSnapshot.key.let { id -> id to transactions }
                    } else {
                        null
                    }

                }
                .toMap()
            Result.success(transactionMap)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}