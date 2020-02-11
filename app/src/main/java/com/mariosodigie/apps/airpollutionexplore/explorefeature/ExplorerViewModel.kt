package com.mariosodigie.apps.airpollutionexplore.explorefeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.BaseViewModel
import com.mariosodigie.apps.airpollutionexplore.utils.OpenForTesting

@OpenForTesting
class ExplorerViewModel(private val explorerService: ExplorerService,
                        context: Context) : BaseViewModel(context) {

    val response = MutableLiveData<String?>()

    fun requestPollutionDetails(location: LatLng){
        explorerService.makeCall(location, ServCallback{
            response.value = it
        })
    }
}