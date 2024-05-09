package com.srabbijan.jobtask.utils

import com.google.gson.Gson
import com.srabbijan.jobtask.data.remote.dto.ErrorResponse
import okhttp3.ResponseBody

/**
 * Getting exact Error Body Response from the API
 */
val ResponseBody?.errorMessage: String?
    get() {
        try {
            val errorResponse: ErrorResponse? = Gson().fromJson<Any>(
                this!!.string(),
                ErrorResponse::class.java
            ) as? ErrorResponse
            errorResponse?.message?.let { messages ->
                return messages
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }