package com.mariosodigie.apps.airpollutionexplore.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import com.mariosodigie.apps.airpollutionexplore.network.Result
import kotlinx.coroutines.flow.Flow

interface PollutionRepository {
    suspend fun getPollutionInfo(
        location: LatLng
    ): Flow<Result<PollutionInfo>>
}