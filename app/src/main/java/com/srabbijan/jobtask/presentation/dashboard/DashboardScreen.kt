package com.srabbijan.jobtask.presentation.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.presentation.commonComponents.EmptyScreen
import com.srabbijan.jobtask.presentation.commonComponents.ErrorScreen
import com.srabbijan.jobtask.presentation.commonComponents.LoadingScreen

@Composable
fun DashboardScreen(state: DashboardState) {
    if (state.isLoading){
        LoadingScreen()
    }
    else{
        if (state.error != null){
            ErrorScreen(state.error)
        }
        else{
            DashboardContent(state.demoData)
        }
    }
}



@Composable
fun DashboardContent(data: List<DemoRemoteData>, modifier: Modifier = Modifier) {
    if (data.isEmpty()){
        EmptyScreen()
    }
    else{
        LazyColumn {
            itemsIndexed(data){
                    index, item ->
                Text(text = item.title?:"")
            }
        }
    }

}