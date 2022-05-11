package com.mariosodigie.apps.airpollutionexplore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import com.mariosodigie.apps.airpollutionexplore.domain.repository.PollutionRepository
import com.mariosodigie.apps.airpollutionexplore.network.NetworkError
import com.mariosodigie.apps.airpollutionexplore.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollutionViewModel @Inject constructor(private val repository: PollutionRepository): ViewModel() {

    private val _pollutionResponse = MutableLiveData<PollutionInfo>()
    val pollutionResponse: LiveData<PollutionInfo> = _pollutionResponse

    private val _networkError = MutableLiveData<NetworkError>()
    val networkError: LiveData<NetworkError> = _networkError

    private val _requestInProgress = MutableLiveData<Boolean>()
    val requestInProgress: LiveData<Boolean> = _requestInProgress

    fun requestPollutionDetails(location: LatLng){
        viewModelScope.launch {
            repository.getPollutionInfo(location).collect{ result ->
                when(result) {
                    is Result.Error -> {
                        _networkError.value = result.error!!
                    }
                    is Result.Success -> {
                        _pollutionResponse.value = result.data!!
                    }
                    is Result.Loading -> _requestInProgress.value = result.isLoading
                }
            }
        }
    }
}