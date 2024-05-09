package com.srabbijan.jobtask.domain.useCases.dashboard

import com.srabbijan.jobtask.domain.repository.DashboardRepository
import com.srabbijan.jobtask.utils.DataResource
import kotlinx.coroutines.flow.Flow

class FetchDemoData(
    private val repository: DashboardRepository
) {
    operator fun invoke(): Flow<DataResource<*>> {
        return repository.fetchDemoData()
    }
}