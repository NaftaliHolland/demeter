package com.mmust.demeter.Models.Auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
