package com.srabbijan.jobtask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int
)
