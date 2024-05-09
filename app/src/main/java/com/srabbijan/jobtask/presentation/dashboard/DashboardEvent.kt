package com.srabbijan.jobtask.presentation.dashboard

sealed class DashboardEvent {
    data object SaveAppEntry: DashboardEvent()
}