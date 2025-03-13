package com.mmust.demeter.data.remote.api

import com.mmust.demeter.data.remote.model.GreenhouseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GreenhouseApi {
    @GET("greenhouses")
    suspend fun fetchGreenhouses(@Query("userId") userId: String): Response<List<GreenhouseDto>>

    @GET("greenhouse")
    suspend fun fetchGreenhouses2(): Response<List<GreenhouseDto>>

}