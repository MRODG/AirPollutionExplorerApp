package com.mariosodigie.apps.airpollutionexplore.api

interface ServiceCallback<T> {

    fun onSuccess(response: T)

    fun onError(error: ApiError)
    fun onError(error: Throwable)
}