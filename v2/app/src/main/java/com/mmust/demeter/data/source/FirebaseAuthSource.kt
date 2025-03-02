package com.mmust.demeter.data.source

import com.google.firebase.auth.FirebaseAuth
import com.mmust.demeter.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthSource(private val firebaseAuth: FirebaseAuth) {
    suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user == null) {
                Result.failure(Exception("User not found"))
            } else {
                val firebaseUser = result.user
                Result.success(User(firebaseUser!!.email ?: "", firebaseUser.uid))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user == null) {
                Result.failure(Exception("Could not sign up user"))
            } else {
                val firebaseUser = result.user
                Result.success(User(firebaseUser!!.email ?: "", firebaseUser.uid))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}