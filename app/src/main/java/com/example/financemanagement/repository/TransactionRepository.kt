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

    private var transactionListener: ValueEventListener? = null

    val transactions = hashMapOf<String, Transactions>()

    suspend fun addTransaction(
        date: Long,
        reason: String,
        amount: Int,
        method: String,
    ) : Result<Unit> {

        val user = FirebaseService.user ?: return Result.failure(IllegalStateException("User is not authenticated."))

        val uid = user.uid
        val key = database.getReference("Users/$uid/Transactions").push().key ?: ""
        try {
            // Add transaction to Firebase
            database.getReference("Users/$uid/Transactions/$key")
                .setValue(Transactions(date, reason, amount,method))
                .await()

            // Fetch current salary and deduct the transaction amount
            val salaryRef = database.getReference("Users/$uid/Salary/Remaining")
            val salarySnapshot = salaryRef.get().await()
            val currentSalary = salarySnapshot.getValue(Int::class.java) ?: 0
            val newSalary = currentSalary - amount

            // Update salary in Firebase
            salaryRef.setValue(newSalary).await()

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }

    fun listenTransactions(
        onChange: (Map<String, Transactions>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            val reference = database.getReference("Users/$uid/Transactions")

            transactionListener?.let { listener ->
                reference.removeEventListener(listener)
            }

            transactionListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { dataSnapshot ->
                        dataSnapshot.key?.let { id ->
                            dataSnapshot.getValue(Transactions::class.java)?.let { transaction ->
                                    transactions[id] = transaction
                            }
                        }
                    }
                    onChange(transactions)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception(error.message))
                }
            }

            reference.addValueEventListener(transactionListener as ValueEventListener)
        }
    }

    private suspend fun getTransactionsSnapshot(): DataSnapshot {
        val user = FirebaseService.user
        if (user != null) {
            val uid = user.uid
            return database.getReference("Users/$uid/Transactions")
                .get().await()
        }
        throw IllegalStateException("User is not authenticated.")
    }

    enum class TransactionMethod(private val method: String) {
        Cash("Cash"),
        NetBanking("Net Banking");

        override fun toString(): String = method
    }

    suspend fun getTransactionByMethod(transactionMethod: TransactionMethod) : Double {
        FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        try {
            val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)

            if (transactionListener == null) {
                val snapshot = getTransactionsSnapshot()
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.key?.let { id ->
                        dataSnapshot.getValue(Transactions::class.java)?.let { transaction ->
                                transactions[id] = transaction
                        }
                    }
                }
            }

            var totalAmount = 0.0

            for (transaction in transactions) {
                val method = transaction.value.method
                val dateEpoch = transaction.value.date
                val amount = transaction.value.amount

                if (method == transactionMethod.toString()) {
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
    }
}
