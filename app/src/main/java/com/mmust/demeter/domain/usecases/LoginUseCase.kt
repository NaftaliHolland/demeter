package com.mmust.demeter.domain.usecases

import com.mmust.demeter.data.remote.model.AuthResponseDto
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun invoke(email: String, password: String): Result<User>{
        return authRepository.login(email, password)
    }
}