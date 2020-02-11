package com.mariosodigie.apps.airpollutionexplore

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.api.ApiError
import com.mariosodigie.apps.airpollutionexplore.api.ServiceCallback
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerService
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerViewModel
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ExplorerServiceTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var explorerService: ExplorerService

    @Mock
    lateinit var location : LatLng


    @Test
    fun pollutionDataCall(){

        val json = "{data : response}"

        doAnswer {
            it.getArgument<ServiceCallback<String?>>(1).onSuccess(json)
        }.whenever(explorerService).makeCall(any(), any())

        val viewModel = ExplorerViewModel(explorerService, context)

        val observer = mock<Observer<String?>>()
        viewModel.response.observeForever(observer)

        viewModel.requestPollutionDetails(location)

        verify(explorerService, times(1)).makeCall(any(), any())
        verify(observer).onChanged(json)
    }

    @Test
    fun pollutionDataCallWhenOffline(){

        val json = "{data : response}"
        val serviceError = ServiceError("","No internet connection detected")

        doAnswer {
            it.getArgument<ServiceCallback<String?>>(1).onError(ApiError.PhoneOffline)
        }.whenever(explorerService).makeCall(any(), any())

        val viewModel = ExplorerViewModel(explorerService, context)

        val observerResponce = mock<Observer<String?>>()
        viewModel.response.observeForever(observerResponce)

        val observerError = mock<Observer<ServiceError>>()
        viewModel.serviceError.observeForever(observerError)

        viewModel.requestPollutionDetails(location)

        verify(explorerService, times(1)).makeCall(any(), any())
        verify(observerResponce, never()).onChanged(json)
        verify(observerError).onChanged(serviceError)
    }

}