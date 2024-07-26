package com.example.financemanagement.repository

import com.example.financemanagement.services.FirebaseService
import kotlinx.coroutines.tasks.await

object ReasonRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }
    private val user = FirebaseService.user

    suspend fun addReason(reason: String) {
        if (user != null) {
            val uid = user.uid
            val key = database.getReference("Users/$uid/Reason").push().key ?: ""
            try {
                database.getReference("Users/$uid/Reason/$key")
                    .setValue(reason)
                    .await()
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }

    suspend fun updateReason(key: String, newReason: String) {
        if (user != null) {
            val uid = user.uid
            try {
                database.getReference("Users/$uid/Reason/$key")
                    .setValue(newReason)
                    .await()
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }

    suspend fun deleteReason(key: String) {
        if (user != null) {
            val uid = user.uid
            try {
                database.getReference("Users/$uid/Reason/$key")
                    .removeValue()
                    .await()
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw IllegalStateException("User is not authenticated.")
        }
    }
}