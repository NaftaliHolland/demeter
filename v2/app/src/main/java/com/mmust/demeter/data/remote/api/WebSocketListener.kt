package com.mmust.demeter.data.remote.api

import com.google.gson.Gson
import com.mmust.demeter.domain.model.DeviceMetric
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class DeviceWebSocketListener(
    private val onMessageReceived: (DeviceMetric) -> Unit
): WebSocketListener() {
    private val gson = Gson()


    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("WebSocket connection oppened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val message = gson.fromJson(text, DeviceMetric::class.java)
        onMessageReceived(message)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {}

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        println("Websocket closing: $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println("Websocket error: ${t.message}")
    }
}