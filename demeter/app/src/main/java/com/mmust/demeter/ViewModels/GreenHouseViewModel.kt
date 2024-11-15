package com.mmust.demeter.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.Models.GreenHouse
import com.mmust.demeter.network.GreenHouseApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GreenHouseViewModel : ViewModel() {
    private val _greenHouses: MutableStateFlow<List<GreenHouse>> = MutableStateFlow(listOf())
    val greenHouses: StateFlow<List<GreenHouse>> = _greenHouses

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error

    init {
        getGreenHouses()
    }

    private fun getGreenHouses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val greenHouses = GreenHouseApi.apiService.getGreenHouses()
                _greenHouses.value = greenHouses
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}