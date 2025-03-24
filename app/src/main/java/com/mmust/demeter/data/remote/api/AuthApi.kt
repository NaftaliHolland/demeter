package com.mmust.demeter.data.remote.api

import com.mmust.demeter.data.remote.model.AuthRequestDto
import com.mmust.demeter.data.remote.model.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("signup")
    suspend fun signup(
        @Body request: AuthRequestDto
    ): AuthResponseDto

    @POST("signin")
    suspend fun login(
        @Body request: AuthRequestDto,
    ): AuthResponseDto
}