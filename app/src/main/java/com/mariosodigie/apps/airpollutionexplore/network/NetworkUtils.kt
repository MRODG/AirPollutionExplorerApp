package com.mariosodigie.apps.airpollutionexplore.network

import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

inline fun <T> safeCall(connectivityCheck: ConnectivityCheck, crossinline action: suspend () -> Result<T>): Flow<Result<T>> {
    return flow{
        emit(Result.Loading(true))
        try {
            if(!connectivityCheck.isConnectedToNetwork()){
                emit(Result.Error(NetworkError.PhoneOffline))
                return@flow
            }
            emit(action())
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Result.Error(NetworkError.Generic))
        } catch (e: HttpException) {
            if (e.code() == 404) emit(Result.Error(NetworkError.OutRegion))
            else emit(Result.Error(NetworkError.Generic))
        } finally {
            emit(Result.Loading(false))
        }
    }
}
