package com.mmust.demeter.data.repository

import android.util.Log
import com.mmust.demeter.data.local.dao.DeviceDao
import com.mmust.demeter.data.local.dao.GreenhouseDao
import com.mmust.demeter.data.local.entity.GreenhouseEntity
import com.mmust.demeter.data.local.entity.toDomain
import com.mmust.demeter.data.local.entity.toDomainList
import com.mmust.demeter.data.remote.api.GreenhouseApi
import com.mmust.demeter.data.remote.model.DeviceResponse
import com.mmust.demeter.data.remote.model.GreenhouseDto
import com.mmust.demeter.data.remote.model.GreenhouseResponse
import com.mmust.demeter.data.remote.model.toEntityList
import com.mmust.demeter.domain.model.Device
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.repository.GreenhouseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class GreenhouseRepositoryImpl(
    private val api: GreenhouseApi,
    private val greenhouseDao: GreenhouseDao,
    private val deviceDao: DeviceDao
    ): GreenhouseRepository {
    override fun getGreenhouses(userId: String): Flow<List<Greenhouse>> {
        return greenhouseDao.getGreenhouses(userId).map { it.toDomainList() }
    }

    override fun getDevices(greenhouseId: String): Flow<List<Device>> {
        return deviceDao.getDevices(greenhouseId).map { it.toDomainList() }
    }

    override suspend fun refreshDevices(greenhouseId: String): Result<Unit> {
       return try {
           val response: Response<DeviceResponse> = api.fetchDevices(greenhouseId)
           if (response.isSuccessful) {
               // Save to room
               response.body()?.let { deviceResponse ->
                   deviceDao.insertDevices(deviceResponse.data.toEntityList())
                   return Result.success(Unit)
               } ?: Result.failure(Exception("Empty response body"))
           } else {
               Result.failure(Exception("API error: ${response.code()}"))
           }
       } catch (e: Exception) {
           Result.failure(e)
       }
    }

    override suspend fun refreshGreenhouses(userId: String): Result<Unit> {
        return try {
            val response: Response<GreenhouseResponse> = api.fetchGreenhouses(userId)
            //val response: Response<List<GreenhouseDto>> = api.fetchGreenhouses2()
            if (response.isSuccessful) {
                response.body()?.let { greenhouseResponse ->
                    greenhouseDao.insertGreenhouses(greenhouseResponse.data.toEntityList())
                    Result.success(Unit)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGreenhouseById(id: String): Greenhouse? {
        return greenhouseDao.getGreenhouseById(id)?.toDomain()
    }

    override suspend fun saveGreenhouses(greenhouses: List<Greenhouse>): Result<Unit> {
        return try {
            // Convert domain Greenhouse to GreenhouseEntity for Room
            val entities = greenhouses.map { greenhouse ->
                GreenhouseEntity(
                    id = greenhouse.id,
                    name = greenhouse.name,
                    photo = greenhouse.photo,
                    location = greenhouse.location,
                    plant = greenhouse.plant,
                    userId = "" // Assume userId isn’t in Greenhouse; set a default or fetch from context
                )
            }
            greenhouseDao.insertGreenhouses(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}