package com.srabbijan.jobtask.presentation.dashboard

import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData

data class DashboardState(
    val demoData: List<DemoRemoteData> = arrayListOf(),
    val isLoading: Boolean = true,
    val error: String? = null
)