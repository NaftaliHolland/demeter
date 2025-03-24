package com.mmust.demeter.data.remote.model

import com.mmust.demeter.data.local.entity.DeviceEntity
import com.mmust.demeter.domain.model.Device

data class DeviceResponse(
    val success: Boolean,
    val message: String,
    val data: List<DeviceDto>
)
data class DeviceDto(
    val id: String,
    val userId: String,
    val greenhouseId: String,
    val type: String
)

fun DeviceDto.toEntity(): DeviceEntity {
   return  DeviceEntity(
       id = id,
       userId = userId,
       greenhouseId = greenhouseId,
       type = type
   )
}

fun List<DeviceDto>.toEntityList(): List<DeviceEntity> = map { it.toEntity() }