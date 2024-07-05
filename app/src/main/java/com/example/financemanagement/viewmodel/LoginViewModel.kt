package com.example.financemanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var user: FirebaseUser? by mutableStateOf(null)
    var errorMessage by mutableStateOf("")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        user = auth.currentUser
    }

    fun onUsernameChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun logIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser
                        onSuccess()
                    } else {
                        errorMessage = task.exception?.message ?: "Authentication failed."
                        onError(errorMessage)
                    }
                }
        }
    }

    fun signIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser
                        onSuccess()
                    } else {
                        errorMessage = task.exception?.message ?: "Account creation failed."
                        onError(errorMessage)
                    }
                }
        }
    }

    fun signOut() {
        auth.signOut()
        user = null
    }
}
