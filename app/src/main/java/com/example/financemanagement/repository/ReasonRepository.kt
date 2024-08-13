package com.example.financemanagement.repository

import com.example.financemanagement.services.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

object ReasonRepository {

    private val database by lazy { FirebaseService.firebaseDatabase }

    suspend fun addReason(reason: String) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        val key = database.getReference("Users/$uid/Reasons").push().key ?: ""
        try {
            database.getReference("Users/$uid/Reasons/$key")
                .setValue(reason)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun updateReason(key: String, newReason: String) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            database.getReference("Users/$uid/Reasons/$key")
                .setValue(newReason)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun deleteReason(key: String) {
        val user = FirebaseService.user ?: throw IllegalStateException("User is not authenticated.")
        val uid = user.uid
        try {
            database.getReference("Users/$uid/Reasons/$key")
                .removeValue()
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getReasons(onChange: (Map<String, String>) -> Unit, onFailure: (Exception) -> Unit) {
        val user = FirebaseService.user
        if (user != null){
            val uid = user.uid
            database.getReference("Users/$uid/Reasons")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val reasons = hashMapOf<String, String>()
                        snapshot.children.forEach { dataSnapshot ->
                            dataSnapshot.key?.let { id ->
                                dataSnapshot.getValue(String::class.java)?.let { reason ->
                                    reasons[id] = reason
                                }
                            }
                        }

                        onChange(reasons)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onFailure(Exception(error.message))
                    }
                }
                )
        }else{
            onFailure(Exception("User is not authenticated."))
        }


    }

}