package com.mariosodigie.apps.airpollutionexplore

import android.app.Application
import com.mariosodigie.apps.airpollutionexplore.di.appModule
import com.mariosodigie.apps.airpollutionexplore.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}