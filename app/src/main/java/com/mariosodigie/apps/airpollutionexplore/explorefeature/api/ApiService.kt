package com.mariosodigie.apps.airpollutionexplore.explorefeature.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("nearest_city")
    fun getResponseByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("key") apiKey: String
    ): Call<ResponseBody>

    @GET("countries")
    fun getSupportedCities(
        @Query("key") apiKey: String
    ): Call<List<Any>>
}