package com.srabbijan.jobtask.data.remote


import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/14a27bd0bcd0cd323b35ad79cf3b493dddf6216b/videos.json")
    suspend fun fetchData():Response<List<DemoRemoteData>>
}