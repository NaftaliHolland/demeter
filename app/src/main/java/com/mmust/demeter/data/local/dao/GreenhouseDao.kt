package com.mmust.demeter.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmust.demeter.data.local.entity.GreenhouseEntity
import com.mmust.demeter.domain.model.Greenhouse
import kotlinx.coroutines.flow.Flow

@Dao
interface GreenhouseDao {
    @Query("SELECT * FROM greenhouses WHERE userId = :userId")
    fun getGreenhouses(userId: String): Flow<List<GreenhouseEntity>>

    @Query("SELECT * FROM greenhouses WHERE id = :id LIMIT 1")
    suspend fun getGreenhouseById(id: String): GreenhouseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGreenhouses(greenhouses: List<GreenhouseEntity>)

    @Query("DELETE FROM greenhouses")
    suspend fun clearGreenhouses()
}