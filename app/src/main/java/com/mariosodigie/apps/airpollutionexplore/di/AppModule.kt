package com.mariosodigie.apps.airpollutionexplore.di

import android.content.Context
import com.mariosodigie.apps.airpollutionexplore.BuildConfig
import com.mariosodigie.apps.airpollutionexplore.data.remote.PollutionApi
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideConnectivityCheck(
        @ApplicationContext appContext: Context
    ): ConnectivityCheck = ConnectivityCheck(appContext)
}