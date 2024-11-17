package com.mmust.demeter.ViewModels.Auth

import androidx.lifecycle.ViewModel
import com.mmust.demeter.Models.Auth.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AuthViewModel() : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()


    fun resetState() {
        _state.update { SignInState() }
    }
}

