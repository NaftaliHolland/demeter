package com.mmust.demeter.presentation.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean,
    val user: User?,
    val error: String?,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(true, null, null))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
           _uiState.update { it.copy(isLoading = true)}
            val result = loginUseCase.invoke(email, password)
            val user = result.getOrNull()
            val error = result.exceptionOrNull()
            if (user != null) {
                _uiState.update{ it.copy(isLoading = false, user = user, error = null) }
            } else {
                _uiState.update{ it.copy(isLoading = false, user = null, error = error?.message) }
            }
        }
    }
}