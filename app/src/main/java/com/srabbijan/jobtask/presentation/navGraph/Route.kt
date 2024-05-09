package com.srabbijan.jobtask.presentation.navGraph

sealed class Route (
    val route: String
){
    data object AppStartNavigation : Route("appStartNavigation")
    data object DashboardNavigationScreen : Route("dashboardNavigationScreen")
    data object VideoPlayerScreen : Route("videoPlayerScreen")
}