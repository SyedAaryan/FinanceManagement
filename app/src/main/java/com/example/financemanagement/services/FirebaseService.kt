package com.example.financemanagement.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://financemanagement-590d8-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    fun getUserDatabaseReference(userId: String? = FirebaseAuth.getInstance().currentUser?.uid): DatabaseReference {
        return firebaseDatabase.reference.child("users").child(userId ?: "")
    }
}