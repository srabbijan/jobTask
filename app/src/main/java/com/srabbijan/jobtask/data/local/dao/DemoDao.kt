package com.srabbijan.jobtask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.srabbijan.jobtask.data.local.entity.DemoEntity

@Dao
interface DemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertData(data: DemoEntity)

}