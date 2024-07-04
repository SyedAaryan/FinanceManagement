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
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var user: FirebaseUser? by mutableStateOf(null)
    var errorMessage by mutableStateOf("")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun signIn(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(username, password)
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

    fun createAccount(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(username, password)
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
