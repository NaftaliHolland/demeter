package com.mmust.demeter.data.repository

import android.content.Context
import com.mmust.demeter.data.remote.api.AuthApi
import com.mmust.demeter.data.remote.model.AuthRequestDto
import com.mmust.demeter.data.remote.model.AuthResponseDto
import com.mmust.demeter.data.remote.model.LoginRequestDto
//import com.mmust.demeter.data.source.FirebaseAuthSource
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.repository.AuthRepository

class AuthRepositoryImpl(
private val authApi: AuthApi
): AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            //val response = authApi.login(AuthRequestDto(email, password))
            val response = authApi.login(LoginRequestDto(email, password))
            Result.success(User(
                email = response.data.email,
                localId = response.data.localId,
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
        //return firebaseAuthSource.login(email, password)
    }
    override suspend fun signUp(email: String, password: String): Result<User> {
        return  try {
           val response = authApi.signup(AuthRequestDto(email, password))
            Result.success(User(
                email = response.data.email,
                localId = response.data.localId,
            ))
        } catch(e: Exception) {
            Result.failure(e)
        }
        //return firebaseAuthSource.signUp(email, password)
    }
}