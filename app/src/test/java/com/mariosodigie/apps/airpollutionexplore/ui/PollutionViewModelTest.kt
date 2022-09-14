package com.mariosodigie.apps.airpollutionexplore.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.MainCoroutineRule
import com.mariosodigie.apps.airpollutionexplore.data.mapper.toPollutionInfo
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import com.mariosodigie.apps.airpollutionexplore.domain.repository.PollutionRepository
import com.mariosodigie.apps.airpollutionexplore.network.NetworkError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.mariosodigie.apps.airpollutionexplore.network.Result
import com.mariosodigie.apps.getPollutionInfoDtoFixture
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class PollutionViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: PollutionRepository

    private lateinit var viewModel: PollutionViewModel

    @Before
    fun setUp(){
        viewModel = PollutionViewModel(repository)
    }

    @Test
    fun requestPollutionDetailsTest() = runTest {

        val location = LatLng(100.0, 100.0)
        //val pollutionDetailsResult = mock<Flow<Result<PollutionInfo>>>()
        val data = getPollutionInfoDtoFixture().toPollutionInfo()

        //replicating flow of network call
        val resultFlow = flow<Result<PollutionInfo>> {
            emit(Result.Loading(true))
            emit(Result.Success(data))
            emit(Result.Loading(false))

        }
        whenever(repository.getPollutionInfo(location)).thenReturn(resultFlow)

        val pollutionResponseObserver = mock<Observer<PollutionInfo>>()
        viewModel.pollutionResponse.observeForever(pollutionResponseObserver)

        viewModel.requestPollutionDetails(location)

        verify(repository).getPollutionInfo(location)
        verify(pollutionResponseObserver).onChanged(data)
    }

    @Test
    fun requestPollutionDetailsWithFailureTest() = runTest {

        val location = LatLng(100.0, 100.0)
        //val pollutionDetailsResult = mock<Flow<Result<PollutionInfo>>>()
        val data = getPollutionInfoDtoFixture().toPollutionInfo()

        //replicating flow of network call
        val resultFlow = flow<Result<PollutionInfo>> {
            emit(Result.Loading(true))
            emit(Result.Error(NetworkError.Generic))
            emit(Result.Loading(false))

        }
        whenever(repository.getPollutionInfo(location)).thenReturn(resultFlow)

        val networkErrorObserver = mock<Observer<NetworkError>>()
        viewModel.networkError.observeForever(networkErrorObserver)

        val pollutionResponseObserver = mock<Observer<PollutionInfo>>()
        viewModel.pollutionResponse.observeForever(pollutionResponseObserver)

        viewModel.requestPollutionDetails(location)

        verify(repository).getPollutionInfo(location)
        verify(pollutionResponseObserver, times(0)).onChanged(any())
        verify(networkErrorObserver, times(1)).onChanged(NetworkError.Generic)
    }
}