package com.mmust.demeter.network

import com.mmust.demeter.Models.GreenHouse
import com.mmust.demeter.Models.Temperature
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://6734a161a042ab85d11afed3.mockapi.io/green_house/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @GET("green_houses")
    suspend fun getGreenHouses(): List<GreenHouse>
}

object GreenHouseApi {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}