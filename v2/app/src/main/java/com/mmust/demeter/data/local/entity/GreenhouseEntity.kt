package com.mmust.demeter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmust.demeter.domain.model.Greenhouse
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "greenhouses")
data class GreenhouseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val photo: String,
    val location: String,
    val plant: String,
    @ColumnInfo(name = "userId") val userId: String
)

fun GreenhouseEntity.toDomain(): Greenhouse {
    return Greenhouse(
        id = id,
        name = name,
        photo = photo,
        location = location,
        plant = plant,
    )
}

fun List<GreenhouseEntity>.toDomainList(): List<Greenhouse> = map { it.toDomain() }