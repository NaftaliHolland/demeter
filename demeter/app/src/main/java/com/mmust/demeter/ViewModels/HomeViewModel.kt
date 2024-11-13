package com.mmust.demeter.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.network.GreenHouseApi
import kotlinx.coroutines.flow.MutableStateFlow
import com.mmust.demeter.Models.Temperature
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    private val _temperatureData : MutableStateFlow<List<Temperature>> = MutableStateFlow(listOf())
    val temperatureData : StateFlow<List<Temperature>> = _temperatureData

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error

    init {
        getTemperature()
    }

    private fun getTemperature() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val temperatures = GreenHouseApi.apiService.getTemperature()
                _temperatureData.value = temperatures
                println(temperatures + "Herehere")
            } catch (e: Exception){
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}