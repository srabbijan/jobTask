package com.srabbijan.jobtask.data.repositoryImpl

import com.srabbijan.jobtask.data.remote.ApiServices
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.domain.repository.DashboardRepository
import com.srabbijan.jobtask.utils.DataResource
import com.srabbijan.jobtask.utils.ErrorType
import com.srabbijan.jobtask.utils.Status
import com.srabbijan.jobtask.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DashboardRepositoryImpl(
    private val apiServices: ApiServices
) : DashboardRepository {

    override fun fetchDemoData(): Flow<DataResource<*>> = flow {

        val data = safeApiCall(Dispatchers.IO) {
            apiServices.fetchData()
        }
        when (data.status) {

            Status.SUCCESS -> {
                val simpleResponse: List<DemoRemoteData> =
                    (data.data as? List<DemoRemoteData>) ?: return@flow emit(
                        DataResource.error(
                            errorType = ErrorType.UNKNOWN,
                            code = 0,
                            message = "Something went wrong",
                            data = null
                        )
                    )

                emit(DataResource.success(data = simpleResponse))
            }

            Status.ERROR -> {
                emit(
                    DataResource.error(
                        errorType = ErrorType.API,
                        code = data.code,
                        message = data.message,
                        data = null
                    )
                )
            }

            Status.LOADING -> {
                emit(DataResource.loading(null))
            }
        }
    }

}