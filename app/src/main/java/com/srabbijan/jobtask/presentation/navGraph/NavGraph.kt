package com.srabbijan.jobtask.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.srabbijan.jobtask.presentation.dashboard.DashboardScreen
import com.srabbijan.jobtask.presentation.dashboard.DashboardViewModel

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
                )
            }
        }

    }

}