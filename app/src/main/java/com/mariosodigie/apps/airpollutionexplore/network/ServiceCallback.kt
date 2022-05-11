package com.mariosodigie.apps.airpollutionexplore.api

import com.mariosodigie.apps.airpollutionexplore.utils.ApiError

interface ServiceCallback<T> {

    fun onSuccess(response: T)

    fun onError(error: ApiError)
    fun onError(error: Throwable)
}