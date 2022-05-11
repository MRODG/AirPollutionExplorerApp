package com.mariosodigie.apps.airpollutionexplore.utils

import com.mariosodigie.apps.airpollutionexplore.data.mapper.toPollutionInfo
import retrofit2.HttpException
import java.io.IOException

inline fun <T> safeCall(connectivityCheck: ConnectivityCheck, action: () -> Result<T>): Result<T> {
    return try {
        if(!connectivityCheck.isConnectedToNetwork()){
            return Result.Error(ApiError.PhoneOffline)
        }
        action()
    } catch (e: IOException) {
        e.printStackTrace()
        Result.Error(ApiError.Generic)
    } catch (e: HttpException) {
        if (e.code() == 404) Result.Error(ApiError.OutRegion)
        else Result.Error(ApiError.Generic)
    }
}
