package com.mmust.demeter.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketService @Inject constructor() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connectWebSocket(uid: String, listener: WebSocketListener) {
        val url = "https://bfd7-80-187-84-199.ngrok-free.app/api/ws?uid=$uid"
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnecting")
    }
}