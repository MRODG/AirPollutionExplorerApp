package com.mariosodigie.apps.airpollutionexplore.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.data.mapper.toPollutionInfo
import com.mariosodigie.apps.airpollutionexplore.data.remote.PollutionApi
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import com.mariosodigie.apps.airpollutionexplore.domain.repository.PollutionRepository
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import com.mariosodigie.apps.airpollutionexplore.network.Result
import com.mariosodigie.apps.airpollutionexplore.network.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PollutionRepositoryImpl @Inject constructor(
    private val pollutionApi: PollutionApi,
    private val connectivityCheck: ConnectivityCheck
):PollutionRepository {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getPollutionInfo(location: LatLng): Flow<Result<PollutionInfo>> {
        return safeCall(connectivityCheck) {
            withContext(Dispatchers.IO){
                val result = pollutionApi.getPollutionByLocation(location.latitude, location.longitude)
                Result.Success(result.toPollutionInfo())
            }
        }
    }
}