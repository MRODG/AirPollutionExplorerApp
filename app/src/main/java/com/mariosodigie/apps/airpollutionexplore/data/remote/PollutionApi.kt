package com.mariosodigie.apps.airpollutionexplore.data.remote

import com.mariosodigie.apps.airpollutionexplore.BuildConfig
import com.mariosodigie.apps.airpollutionexplore.data.remote.dto.PollutionInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PollutionApi {

    @GET("nearest_city")
    suspend fun getPollutionByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("key") apiKey: String = BuildConfig.EXPLORE_API_KEY
    ): PollutionInfoDto
}