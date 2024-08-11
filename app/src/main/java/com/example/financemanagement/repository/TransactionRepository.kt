package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import java.util.Calendar

object TransactionRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    suspend fun addTransaction(
        date: Long,
        reason: String,
        amount: Int,
        method: String,
        onSuccess: (key: String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            val key = database.getReference("Users/$uid/Transactions").push().key ?: ""
            try {
                // Add transaction to Firebase
                database.getReference("Users/$uid/Transactions/$key")
                    .setValue(Transactions(date, reason, amount,method))
                    .await()

                // Fetch current salary and deduct the transaction amount
                val salaryRef = database.getReference("Users/$uid/Remaining Salary")
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

    fun listenTransactions(
        onChange: (Map<String, Transactions>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = FirebaseService.user
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

    private suspend fun getTransactionsSnapshot(): DataSnapshot {
        val user = FirebaseService.user;
        if (user != null) {
            val uid = user.uid
            return database.getReference("Users/$uid/Transactions")
                .get().await()
        }
        throw IllegalStateException("User is not authenticated.");
    }

    enum class TransactionMethod(private val method: String) {
        Cash("Cash"),
        NetBanking("Net Banking");

        override fun toString(): String = method
    }

    suspend fun getTransactionByMethod(transactionMethod: TransactionMethod) : Double {
        val user = FirebaseService.user
        if (user != null) {
            try {
                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                val snapshot = getTransactionsSnapshot()

                var totalAmount = 0.0

                for (transaction in snapshot.children) {
                    val method = transaction.child("method").getValue(String::class.java)
                    val dateEpoch = transaction.child("date").getValue(Long::class.java)
                    val amount = transaction.child("amount").getValue(Double::class.java)

                    if (method == transactionMethod.toString() && dateEpoch != null && amount != null) {
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
}
