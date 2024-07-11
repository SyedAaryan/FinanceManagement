package com.example.financemanagement.repository

import com.example.financemanagement.model.Transactions
import com.example.financemanagement.services.FirebaseService

object TransactionRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    fun addTransaction(date: Long, reason: String, amount: Int,onSuccess: (key: String) -> Unit, onFailure: (Exception) -> Unit){
        val user = FirebaseService.user
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

//    fun addEvent(date: Long, place: String, onSuccess: (key: String) -> Unit, onFailure: (Exception) -> Unit) {
//        val key = database.getReference("events").push().key ?: ""
//        database.getReference("events/$key")
//            .setValue(Event(date, place))
//            .addOnSuccessListener {
//                onSuccess(key)
//            }
//            .addOnFailureListener { e ->
//                onFailure(e)
//            }
//    }

}