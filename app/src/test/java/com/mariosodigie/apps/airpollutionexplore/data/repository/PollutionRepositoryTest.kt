package com.mariosodigie.apps.airpollutionexplore.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.MainCoroutineRule
import com.mariosodigie.apps.airpollutionexplore.data.mapper.toPollutionInfo
import com.mariosodigie.apps.airpollutionexplore.data.remote.FakePollutionApi
import com.mariosodigie.apps.airpollutionexplore.data.remote.dto.PollutionInfoDto
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import com.mariosodigie.apps.airpollutionexplore.domain.repository.PollutionRepository
import com.mariosodigie.apps.airpollutionexplore.network.NetworkError
import com.mariosodigie.apps.airpollutionexplore.network.Result
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import com.mariosodigie.apps.getPollutionInfoDtoFixture
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.HttpException
import retrofit2.Response

class PollutionRepositoryTest {

    private lateinit var repo: PollutionRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var pollutionApi: FakePollutionApi

    @Mock
    lateinit var connectivityCheck: ConnectivityCheck

    @Before
    fun setUp() {
        pollutionApi = FakePollutionApi()
        repo = PollutionRepositoryImpl(pollutionApi, connectivityCheck)
    }

    @Test
    fun getPollutionInfoTest() = runTest {

        val location = LatLng(100.0, 100.0)

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)

        val emissionsResult: List<Result<PollutionInfo>> = repo.getPollutionInfo(location).toList()

        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().city, emissionsResult[1].data!!.city)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().state, emissionsResult[1].data!!.state)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().country, emissionsResult[1].data!!.country)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().qualityIndex, emissionsResult[1].data!!.qualityIndex)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().temperature, emissionsResult[1].data!!.temperature)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().humidity, emissionsResult[1].data!!.humidity)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().icon, emissionsResult[1].data!!.icon)
        assertEquals(getPollutionInfoDtoFixture().toPollutionInfo().wind, emissionsResult[1].data!!.wind)
    }

    @Test
    fun getPollutionInfoWhenOfflineTest() = runTest {

        val location = LatLng(100.0, 100.0)

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(false)

        val emissionsResult: List<Result<PollutionInfo>> = repo.getPollutionInfo(location).toList()

        assertEquals(NetworkError.PhoneOffline, emissionsResult[1].error)
    }

    @Test
    fun getPollutionInfoWithIOExceptionTest() = runTest {

        val location = LatLng(100.0, 100.0)

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)


        pollutionApi.setReturnException(Exception("io exception"))

        val emissionsResult: List<Result<PollutionInfo>> = repo.getPollutionInfo(location).toList()

        assertEquals(NetworkError.Generic, emissionsResult[1].error)
    }

    @Test
    fun getPollutionInfoWhenOutRegionTest() = runTest {

        val location = LatLng(100.0, 100.0)

        whenever(connectivityCheck.isConnectedToNetwork()).thenReturn(true)


        val response = mock<Response<PollutionInfoDto>>()
        whenever(response.code()).thenReturn(404)
        val httpException = HttpException(response)

        pollutionApi.setReturnException(httpException)

        val emissionsResult: List<Result<PollutionInfo>> = repo.getPollutionInfo(location).toList()

        assertEquals(NetworkError.OutRegion, emissionsResult[1].error)
    }
}