package com.srabbijan.jobtask.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.presentation.dashboard.DashboardScreen
import com.srabbijan.jobtask.presentation.dashboard.DashboardViewModel
import com.srabbijan.jobtask.presentation.videoPlayScreen.VideoPlayerScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.DashboardNavigationScreen.route
        ) {
            composable(
                route = Route.DashboardNavigationScreen.route
            ) {
                val viewModel: DashboardViewModel = hiltViewModel()
                val state = viewModel.state.value
                DashboardScreen(
                    state = state,
                    event = viewModel::onEvent
                ) {
                    navigateToVideoPlayer(
                        navController = navController,
                        data = it
                    )
                }
            }
            composable(route = Route.VideoPlayerScreen.route) {
//                val viewModel: DetailsViewModel = hiltViewModel()
//                if (viewModel.sideEffect != null) {
//                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
//                        .show()
//                    viewModel.onEvent(DetailsEvent.removeSideEffect)
//                }
                navController.previousBackStackEntry?.savedStateHandle?.get<DemoRemoteData?>("data")
                    ?.let {
                        VideoPlayerScreen(
                            data = it,
//                            event = viewModel::onEvent,
                            navigateUp = {
                                navController.navigateUp()
                            }
                        )
                    }
            }
        }

    }

}

private fun navigateToVideoPlayer(
    navController: NavController,
    data: DemoRemoteData
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("data", data)
    navController.navigate(
        route = Route.VideoPlayerScreen.route
    )
}