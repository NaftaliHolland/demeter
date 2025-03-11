package com.mmust.demeter.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.model.User
import com.mmust.demeter.domain.repository.GreenhouseRepository
import com.mmust.demeter.domain.usecases.GetGreenhousesUseCase
import com.mmust.demeter.domain.usecases.RefreshGreenhousesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val greenhouses: List<Greenhouse> = emptyList()
)

@HiltViewModel
class GreenhouseViewModel @Inject constructor(
    private val getGreenhousesUseCase: GetGreenhousesUseCase,
    private val refreshGreenhousesUseCase: RefreshGreenhousesUseCase
): ViewModel() {
    //private val _greenhouses = MutableStateFlow<List<Greenhouse>>(emptyList())
    //val greenhouses: StateFlow<List<Greenhouse>> = _greenhouses.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetchGreenhouses(userId: String) {
        viewModelScope.launch {
            getGreenhousesUseCase(userId)
                .onStart { _uiState.update { it.copy(isLoading = true) } } // Show loading state
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { greenhouses ->
                    _uiState.update { it.copy(greenhouses = greenhouses, isLoading = false) }
                }
        }
    }

    fun refreshGreenhouses(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = refreshGreenhousesUseCase(userId)
            if (result.isSuccess) {
                fetchGreenhouses(userId)
            } else {
                _uiState.update { it.copy(error = result.exceptionOrNull()?.message ?: "Could not fetch greenhouses") }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    /*init {
        fetchGreenhouses("user123")
        //refreshGreenhouses("user123")
    }

    fun fetchGreenhouses(userId: String) {
        viewModelScope.launch {
            getGreenhousesUseCase(userId)
                .collect { items ->
                    Log.d("GreenhouseViewModel", "Fetched: $items")
                    _greenhouses.value = items
                }
        }
    }

    fun refreshGreenhouses(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = refreshGreenhouseUseCase(userId)
            Log.d("GreenhouseViewModelRefresh", "Refreshed: $result")

            if (result.isSuccess) {
                fetchGreenhouses(userId)
            } else {
                _uiState.update{ it.copy(error = "Could not fetch") }
            }
        }
    }*/
}