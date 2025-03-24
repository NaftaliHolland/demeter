package com.mmust.demeter.data.remote.model

data class AuthResponseDto(
    val success: Boolean,
    val message: String,
    val data: AuthData
)

data class AuthData(
    val email: String,
    val localId: String
)

data class AuthRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)