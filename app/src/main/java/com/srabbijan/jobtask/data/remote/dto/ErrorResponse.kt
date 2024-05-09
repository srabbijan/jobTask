package com.srabbijan.jobtask.data.remote.dto

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("statusCode" ) var statusCode : Int?    = null,
    @SerializedName("status"     ) var status     : String? = null,
    @SerializedName("message"    ) var message    : String? = null
)
