package com.srabbijan.jobtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srabbijan.jobtask.data.local.dao.DemoDao
import com.srabbijan.jobtask.data.local.entity.DemoEntity


@Database(
    entities = [
        DemoEntity::class
    ],
    version = 1
)
abstract class LocalDB : RoomDatabase() {
    abstract val demoDao: DemoDao
}