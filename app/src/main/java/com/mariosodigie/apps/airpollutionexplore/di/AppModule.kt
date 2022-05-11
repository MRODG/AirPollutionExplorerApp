package com.mariosodigie.apps.airpollutionexplore.di

import android.app.Application
import androidx.room.Room
import com.mariosodigie.apps.airpollutionexplore.BuildConfig
import com.mariosodigie.apps.airpollutionexplore.data.remote.PollutionApi
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerService
import com.mariosodigie.apps.airpollutionexplore.explorefeature.api.ApiService
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePollutionApi(): PollutionApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.EXPLORE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    }


    @Binds
    @Singleton
    abstract fun provideConnectivityCheck(
        connectivityCheck: ConnectivityCheck
    ): StockRepository
}