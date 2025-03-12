package com.mmust.demeter.presentation.greenhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.usecases.GetGreenhouseByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    val isLoading: Boolean = false,
    val greenhouse: Greenhouse? = null,
    val error: String? = null
)

@HiltViewModel
class GreenhouseDetailsViewModel @Inject constructor(
   private val getGreenhouseByIdUseCase: GetGreenhouseByIdUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val  uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetchGreenhouse(id: String) {
        _uiState.value = UiState()
       viewModelScope.launch {
           _uiState.update { it.copy(isLoading = true) }
           try {
               val greenhouse = getGreenhouseByIdUseCase(id)
               _uiState.update { it.copy(isLoading = false, greenhouse = greenhouse) }
           } catch (e: NoSuchElementException) {
               _uiState.update { it.copy(isLoading = false, error = e.message) }
           } catch (e: Exception) {
               _uiState.update { it.copy(isLoading = false, error = "An unexpcted error occured") }
           }
       }
    }
}