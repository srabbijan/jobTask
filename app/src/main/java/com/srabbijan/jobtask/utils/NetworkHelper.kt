package com.srabbijan.jobtask.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): DataResource<*> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            response as Response<*>

            if (response.isSuccessful) {
                DataResource.success(
                    data = response.body(),
                    code = response.code()
                )
            } else {
                response.errorBody().errorMessage?.let {
                    DataResource.error(
                        errorType = ErrorType.API,
                        code = response.code(),
                        message = it,
                        data = it
                    )
                } ?: kotlin.run {
                    DataResource.error(
                        errorType = ErrorType.UNKNOWN,
                        code = response.code(),
                        message = null
                    )
                }
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    DataResource.error(
                        errorType = ErrorType.IO,
                        code = null,
                        message = "Please check your internet connection."
                    )
                }
                is HttpException -> {
                    DataResource.error(
                        errorType = ErrorType.NETWORK,
                        code = throwable.code(),
                        message = "Something went wrong! Please try again."
                    )
                }
                else -> {
                    DataResource.error(
                        errorType = ErrorType.UNKNOWN,
                        code = null,
                        message = "Something went wrong! Please try again."
                    )
                }
            }
        }
    }
}