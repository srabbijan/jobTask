package com.srabbijan.jobtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srabbijan.jobtask.data.local.entity.DemoEntity
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData

@Dao
interface DemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertData(data: DemoRemoteData)
    @Query("SELECT * FROM demo_remote_data_table")
    suspend fun fetchLocalData():List<DemoRemoteData>

}