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
) : ViewModel() {

    private val _deviceMetricState = MutableStateFlow<List<DeviceMetric>>(emptyList())
    val deviceMetricState: StateFlow<List<DeviceMetric>> = _deviceMetricState

    fun startWebSocketConnection(uid: String) {
        val listener = DeviceWebSocketListener { newMetric ->
            viewModelScope.launch {
                val currentList = _deviceMetricState.value

                val updatedList = if (currentList.any { it.device_id == newMetric.device_id && it.metric == newMetric.metric }) {
                    currentList.map {
                        if (it.device_id == newMetric.device_id && it.metric == newMetric.metric) {
                            it.copy(value = newMetric.value)
                        } else {
                            it
                        }
                    }
                } else {
                    currentList + newMetric
                }

                _deviceMetricState.value = updatedList
            }
        }

        webSocketService.connectWebSocket(uid, listener)
    }

    override fun onCleared() {
        webSocketService.disconnect()
        super.onCleared()
    }
}
