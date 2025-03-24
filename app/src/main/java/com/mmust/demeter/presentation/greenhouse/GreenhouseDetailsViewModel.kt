package com.mmust.demeter.presentation.greenhouse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.domain.model.Device
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.usecases.GetDevicesUseCase
import com.mmust.demeter.domain.usecases.GetGreenhouseByIdUseCase
import com.mmust.demeter.domain.usecases.RefreshDevicesUseCase
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

data class DevicesState(
    val isLoading: Boolean = false,
    val devices: List<Device> = emptyList(),
    val error: String? = null
)


@HiltViewModel
class GreenhouseDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGreenhouseByIdUseCase: GetGreenhouseByIdUseCase,
    private val getDevicesUseCase: GetDevicesUseCase,
    private val refreshDevicesUseCase: RefreshDevicesUseCase
): ViewModel() {
    val greenhouseId: String = savedStateHandle["id"] ?: ""
    private val _uiState = MutableStateFlow(UiState())
    val  uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _devicesState = MutableStateFlow(DevicesState())
    val devicesState: StateFlow<DevicesState> = _devicesState.asStateFlow()

    //val greenhouseId = "gh-1" // Get this from param or update from screen

    init {
       refreshDevices(greenhouseId)
    }

    fun fetchGreenhouse() {
        _uiState.value = UiState()
       viewModelScope.launch {
           _uiState.update { it.copy(isLoading = true) }
           try {
               val greenhouse = getGreenhouseByIdUseCase(greenhouseId)
               _uiState.update { it.copy(isLoading = false, greenhouse = greenhouse) }
           } catch (e: NoSuchElementException) {
               _uiState.update { it.copy(isLoading = false, error = e.message) }
           } catch (e: Exception) {
               _uiState.update { it.copy(isLoading = false, error = "An unexpcted error occured") }
           }
       }
    }

    fun fetchDevices() {
        _devicesState.value = DevicesState()
        viewModelScope.launch {
            _devicesState.update { it.copy(isLoading = true) }
            try {
                getDevicesUseCase(greenhouseId).collect { devices ->
                    _devicesState.update { it.copy( isLoading = false, devices = devices, error = null) }
                }
            } catch (e: Exception) {
                _devicesState.update { it.copy(isLoading = true, error = e.toString()) }
            }
        }
    }

    fun refreshDevices(greenhouseId: String) {
        viewModelScope.launch {
            _devicesState.update { it.copy(isLoading = true) }
            val result = refreshDevicesUseCase(greenhouseId)
            if (result.isSuccess) {
                fetchDevices()
            } else {
                _devicesState.update { it.copy(error = result.exceptionOrNull()?.message ?: "Could not fetch devices", isLoading = false)}
            }
        }
    }
}