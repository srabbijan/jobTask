package com.srabbijan.jobtask.domain.repository

import com.srabbijan.jobtask.utils.DataResource
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun fetchDemoData(): Flow<DataResource<*>>
}