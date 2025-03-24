package com.mmust.demeter.domain.repository

import com.mmust.demeter.data.remote.model.AuthResponseDto
import com.mmust.demeter.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>
}