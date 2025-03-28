package com.mmust.demeter.domain.repository

import com.mmust.demeter.domain.model.Device
import com.mmust.demeter.domain.model.Greenhouse
import kotlinx.coroutines.flow.Flow

interface GreenhouseRepository {
    fun getGreenhouses(userId: String): Flow<List<Greenhouse>>
    suspend fun refreshGreenhouses(userId: String): Result<Unit>
    suspend fun getGreenhouseById(id: String): Greenhouse?
    suspend fun saveGreenhouses(greenhouse: List<Greenhouse>): Result<Unit>

    fun getDevices(greenhouseId: String): Flow<List<Device>>
    suspend fun refreshDevices(greenhouseId: String): Result<Unit>
}