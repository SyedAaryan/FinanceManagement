package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

object TransactionRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    val user = FirebaseService.user

    fun addTransaction(
        date: Long,
        reason: String,
        amount: Int,
        onSuccess: (key: String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (user != null) {
            val uid = user.uid
            val key = database.getReference("Users/$uid/Transactions").push().key ?: ""
            database.getReference("Users/$uid/Transactions/$key")
                .setValue(Transactions(date, reason, amount))
                .addOnSuccessListener {
                    onSuccess(key)
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
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