package com.mmust.demeter.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.data.source.SessionManager
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
)
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val sessionManger: SessionManager
): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = signUpUseCase.invoke(email, password)
            val user = result.getOrNull()
            val error = result.exceptionOrNull()
            if (user != null) {
                _uiState.update{ it.copy(isLoading = false, user = user, error = null)}
                sessionManger.saveSession(user.uid)
            } else {
                _uiState.update { it.copy(isLoading = false, user = null, error = null)}
            }
        }
    }
}