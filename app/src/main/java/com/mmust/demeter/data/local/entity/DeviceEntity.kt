package com.mmust.demeter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmust.demeter.domain.model.Device

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val greenhouseId: String,
    val type: String
)

fun DeviceEntity.toDomain(): Device {
    return Device(
        device_id = id,
        userId = userId,
        greenhouseId = greenhouseId,
        type = type,
    )
}

fun List<DeviceEntity>.toDomainList(): List<Device> = map {it.toDomain() }