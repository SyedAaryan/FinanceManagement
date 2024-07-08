package com.example.financemanagement.services

import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://financemanagement-590d8-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

}