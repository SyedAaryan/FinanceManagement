package com.example.financemanagement.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {


    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val user: FirebaseUser?
        get() = auth.currentUser



    val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://financemanagement-590d8-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

}