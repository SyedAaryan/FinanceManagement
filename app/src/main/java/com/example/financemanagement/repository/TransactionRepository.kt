package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

object TransactionRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }
    private val user = FirebaseService.user

    suspend fun addTransaction(
        date: Long,
        reason: String,
        amount: Int,
        method: String,
        onSuccess: (key: String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (user != null) {
            val uid = user.uid
            val key = database.getReference("Users/$uid/Transactions").push().key ?: ""
            try {
                // Add transaction to Firebase
                database.getReference("Users/$uid/Transactions/$key")
                    .setValue(Transactions(date, reason, amount,method))
                    .await()

                // Fetch current salary and deduct the transaction amount
                val salaryRef = database.getReference("Users/$uid/salary")
                val salarySnapshot = salaryRef.get().await()
                val currentSalary = salarySnapshot.getValue(Int::class.java) ?: 0
                val newSalary = currentSalary - amount

                // Update salary in Firebase
                salaryRef.setValue(newSalary).await()

                onSuccess(key)
            } catch (e: Exception) {
                onFailure(e)
            }
        } else {
            onFailure(IllegalStateException("User is not authenticated."))
        }
    }

    fun getTransactions(
        onChange: (Map<String, Transactions>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (user != null) {
            val uid = user.uid
            database.getReference("Users/$uid/Transactions")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val transactions = hashMapOf<String, Transactions>()
                        snapshot.children.forEach { dataSnapshot ->
                            dataSnapshot.key?.let { id ->
                                dataSnapshot.getValue(Transactions::class.java)
                                    ?.let { transaction ->
                                        transactions[id] = transaction
                                    }
                            }
                        }
                        onChange(transactions)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onFailure(Exception(error.message))
                    }
                })
        }
    }
}
