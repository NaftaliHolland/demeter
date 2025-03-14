package com.mmust.demeter.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmust.demeter.data.local.dao.DeviceDao
import com.mmust.demeter.data.local.dao.GreenhouseDao
import com.mmust.demeter.data.local.entity.DeviceEntity
import com.mmust.demeter.data.local.entity.GreenhouseEntity

@Database(entities = [GreenhouseEntity::class, DeviceEntity::class], version=1, exportSchema = false)
abstract class GreenhouseDatabase: RoomDatabase() {
    abstract fun greenhouseDao(): GreenhouseDao
    abstract fun deviceDao(): DeviceDao
}