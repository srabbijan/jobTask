package com.srabbijan.jobtask.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.domain.useCases.dashboard.DashboardUseCases
import com.srabbijan.jobtask.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DashboardState())
    val state: State<DashboardState> = _state

    init {
        fetchDemoData()
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.onRefresh -> {
                _state.value = _state.value.copy(isLoading = true)
                fetchDemoData()
            }
        }
    }

    /*    val loginLiveData by lazy { DataBundleFlowData() }
        fun submitLogin() = viewModelScope.callRepoAsFlow(
            Dispatchers.IO, loginLiveData
        ) {
            appEntryUseCases.appSupportedLanguages.invoke()
        }
        */
    private fun fetchDemoData() {
        viewModelScope.launch {
            dashboardUseCases.demoRemoteData().collect { response ->

                Log.d("TAG", "fetchDemoData: $response")
                when (response.status) {
                    Status.SUCCESS -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(error = null)
                        val data = response.data as? List<DemoRemoteData> ?: return@collect
                        _state.value = _state.value.copy(demoData = data)
                    }

                    Status.ERROR -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(error = response.message)
                    }

                    Status.LOADING -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}