package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(email: String, password: String): Result<User> {
        return authRepository.signUp(email, password)
    }
}