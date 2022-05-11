package com.mariosodigie.apps.airpollutionexplore.di

import com.mariosodigie.apps.airpollutionexplore.data.repository.PollutionRepositoryImpl
import com.mariosodigie.apps.airpollutionexplore.domain.repository.PollutionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPollutionRepository(
        pollutionRepositoryImpl: PollutionRepositoryImpl
    ): PollutionRepository
}