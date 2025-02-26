package com.mmust.demeter.data.repository

import android.content.Context
import com.mmust.demeter.data.source.FirebaseAuthSource
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.repository.AuthRepository

class AuthRepositoryImpl(
private val firebaseAuthSource: FirebaseAuthSource
): AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return firebaseAuthSource.login(email, password)
    }
}