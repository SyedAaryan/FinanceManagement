package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanagement.services.FirebaseService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")


    fun logIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            FirebaseService.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        val errorMessage = task.exception?.message ?: "Authentication failed."
                        onError(errorMessage)
                    }
                }
        }
    }

    fun signIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            FirebaseService.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        val errorMessage = task.exception?.message ?: "Account creation failed."
                        onError(errorMessage)
                    }
                }
        }
    }
}
