package com.srabbijan.jobtask.presentation.dashboard

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.presentation.commonComponents.errorViews.EmptyScreen
import com.srabbijan.jobtask.presentation.commonComponents.shimmer.ShimmerEffectHome
import com.srabbijan.jobtask.presentation.dashboard.components.HomeVideoCard

@Composable
fun DashboardScreen(state: DashboardState, event: (DashboardEvent) -> Unit) {
    if (state.isLoading) {
        ShimmerEffectHome()
    } else {
        if (state.error != null) {
            EmptyScreen {
                event.invoke(DashboardEvent.onRefresh)
            }
        } else {
            if (state.demoData.isEmpty()) {
                EmptyScreen() {
                    event.invoke(DashboardEvent.onRefresh)
                }
            }
            DashboardContent(state.demoData)
        }
    }
}


@Composable
fun DashboardContent(data: List<DemoRemoteData>,) {
    LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
        items(data) {
            HomeVideoCard(it)
        }
    }
}