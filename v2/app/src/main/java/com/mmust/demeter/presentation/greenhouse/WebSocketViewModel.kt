package com.mmust.demeter.presentation.greenhouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.data.remote.api.DeviceWebSocketListener
import com.mmust.demeter.data.remote.api.WebSocketService
import com.mmust.demeter.domain.model.DeviceMetric
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebSocketViewModel @Inject constructor(
    private val webSocketService: WebSocketService
): ViewModel() {
    private val _deviceMetricState = MutableStateFlow<DeviceMetric?>(null)
    val deviceMetricState: StateFlow<DeviceMetric?> = _deviceMetricState

    fun startWebSocketConnection(uid: String) {
        val listener = DeviceWebSocketListener { deviceMetric ->
            viewModelScope.launch {
                _deviceMetricState.emit(deviceMetric)
            }
        }
        webSocketService.connectWebSocket(uid, listener)
    }

    override fun onCleared() {
        webSocketService.disconnect()
        super.onCleared()
    }
}