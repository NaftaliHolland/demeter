package com.mmust.demeter.data.repository

import android.util.Log
import com.mmust.demeter.data.local.dao.GreenhouseDao
import com.mmust.demeter.data.local.entity.GreenhouseEntity
import com.mmust.demeter.data.local.entity.toDomain
import com.mmust.demeter.data.local.entity.toDomainList
import com.mmust.demeter.data.remote.api.GreenhouseApi
import com.mmust.demeter.data.remote.model.GreenhouseDto
import com.mmust.demeter.data.remote.model.toEntityList
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.repository.GreenhouseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class GreenhouseRepositoryImpl(
    private val api: GreenhouseApi,
    private val dao: GreenhouseDao
    ): GreenhouseRepository {
    override fun getGreenhouses(userId: String): Flow<List<Greenhouse>> {
        return dao.getGreenhouses(userId).map { it.toDomainList() }
    }

    override suspend fun refreshGreenhouses(userId: String): Result<Unit> {
        return try {
            //val response: Response<List<GreenhouseDto>> = api.fetchGreenhouses(userId)
            val response: Response<List<GreenhouseDto>> = api.fetchGreenhouses2()
            if (response.isSuccessful) {
                response.body()?.let { greenhouses ->
                    dao.insertGreenhouses(greenhouses.toEntityList())
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
        return dao.getGreenhouseById(id)?.toDomain()
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
                    userId = "" // Assume userId isn’t in Greenhouse; set a default or fetch from context
                )
            }
            dao.insertGreenhouses(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}