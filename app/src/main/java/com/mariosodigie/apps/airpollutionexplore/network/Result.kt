package com.mariosodigie.apps.airpollutionexplore.utils

sealed class Result<T>(val data: T? = null, val message: String? = null, val error: ApiError? = null) {
    class Success<T>(data:T?): Result<T>(data)
    class Error<T>(error: ApiError): Result<T>(error = error)
    class Loading<T>(val isLoading: Boolean = true): Result<T>(null)
}