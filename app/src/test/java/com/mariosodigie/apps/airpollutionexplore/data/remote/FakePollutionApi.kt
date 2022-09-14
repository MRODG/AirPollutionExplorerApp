package com.mariosodigie.apps.airpollutionexplore.data.remote

import com.mariosodigie.apps.airpollutionexplore.data.remote.dto.PollutionInfoDto
import com.mariosodigie.apps.airpollutionexplore.utils.OpenForTesting
import com.mariosodigie.apps.getPollutionInfoDtoFixture

@OpenForTesting
class FakePollutionApi: PollutionApi {

    private var exception: Exception? = null

    fun setReturnException(ex: Exception) {
        exception = ex
    }

    override suspend fun getPollutionByLocation(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): PollutionInfoDto {
        return exception?.let { throw it } ?: getPollutionInfoDtoFixture()
    }
}