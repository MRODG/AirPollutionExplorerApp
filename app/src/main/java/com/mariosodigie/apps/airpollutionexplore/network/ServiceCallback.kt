package com.mariosodigie.apps.airpollutionexplore.network

interface ServiceCallback<T> {

    fun onSuccess(response: T)

    fun onError(error: NetworkError)
    fun onError(error: Throwable)
}