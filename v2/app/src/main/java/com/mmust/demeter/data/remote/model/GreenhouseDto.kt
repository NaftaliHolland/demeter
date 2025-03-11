package com.mmust.demeter.data.remote.model

import com.mmust.demeter.data.local.entity.GreenhouseEntity

data class GreenhouseDto(
    val id: String,
    val name: String,
    val location: String,
    val photo: String,
    val userId: String
)

fun GreenhouseDto.toEntity(): GreenhouseEntity {
    return GreenhouseEntity(
        id = id,
        name = name,
        photo = photo,
        location = location,
        userId = userId
    )
}

fun List<GreenhouseDto>.toEntityList(): List<GreenhouseEntity> = map { it.toEntity() }