package com.mariosodigie.apps.airpollutionexplore.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityCheck @Inject constructor(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.M)
    @Suppress("DEPRECATION")
    fun isConnectedToNetwork(): Boolean {
        return context.getSystemService(ConnectivityManager::class.java)?.activeNetworkInfo?.isConnected ?: false
    }
}