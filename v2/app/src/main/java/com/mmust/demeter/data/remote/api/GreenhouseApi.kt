package com.mmust.demeter.data.remote.api

import com.mmust.demeter.data.remote.model.DeviceResponse
import com.mmust.demeter.data.remote.model.GreenhouseDto
import com.mmust.demeter.data.remote.model.GreenhouseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GreenhouseApi {
    @GET("greenhouses")
    suspend fun fetchGreenhouses(@Query("user_id") userId: String): Response<GreenhouseResponse>

    @GET("greenhouse")
    suspend fun fetchGreenhouses2(): Response<List<GreenhouseDto>>

    @GET("devices")
    suspend fun fetchDevices(@Query("greenhouse_id") greenhouseId: String): Response<DeviceResponse>
}