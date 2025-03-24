package com.mmust.demeter.data.remote.model

import com.mmust.demeter.data.local.entity.GreenhouseEntity

data class GreenhouseResponse(
    val success: Boolean,
    val message: String,
    val data: List<GreenhouseDto>
)

data class GreenhouseDto(
    val id: String,
    val userId: String,
    val name: String,
    val photo: String,
    val location: String,
    val plant: String,
)

fun GreenhouseDto.toEntity(): GreenhouseEntity {
    return GreenhouseEntity(
        id = id,
        name = name,
        photo = photo,
        location = location,
        userId = userId,
        plant = plant,
    )
}

fun List<GreenhouseDto>.toEntityList(): List<GreenhouseEntity> = map { it.toEntity() }