package com.mmust.demeter.data.remote.api

import com.mmust.demeter.data.remote.model.AuthRequestDto
import com.mmust.demeter.data.remote.model.AuthResponseDto
import com.mmust.demeter.data.remote.model.LoginRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signup")
    suspend fun signup(
        @Body request: AuthRequestDto
    ): AuthResponseDto

    @POST("auth/signin")
    suspend fun login(
        //@Body request: AuthRequestDto,
        @Body request: LoginRequestDto,
    ): AuthResponseDto
}