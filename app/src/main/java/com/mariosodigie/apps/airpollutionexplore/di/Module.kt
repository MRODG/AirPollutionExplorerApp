package com.mariosodigie.apps.airpollutionexplore.di

import com.mariosodigie.apps.airpollutionexplore.BuildConfig
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerService
import com.mariosodigie.apps.airpollutionexplore.explorefeature.api.ApiService
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { ConnectivityCheck(get()) }
    single { ExplorerService(get(), get()) }


    single {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.EXPLORE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}