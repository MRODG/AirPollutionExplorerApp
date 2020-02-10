package com.mariosodigie.apps.airpollutionexplore.explorefeature

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.mariosodigie.apps.airpollutionexplore.BaseViewModel
import com.mariosodigie.apps.airpollutionexplore.explorefeature.model.PollutionResponse

class PollutionDetailsViewModel(private val explorerService: ExplorerService,
                                context: Context) : BaseViewModel(context) {

    val pollutionObject = MutableLiveData<PollutionResponse>()

    fun getDataObject(data: String){
        explorerService.jsonParser(data)?.run {
            pollutionObject.value = this
        }
    }

    fun getPollutionViewStyle(aqi: Int): AqiLevel{
        return when(aqi){
            in 0..50 -> AqiLevel.Good
            in 51..100 -> AqiLevel.Moderate
            in 101..150 -> AqiLevel.UnhealthySensitive
            in 151..200 -> AqiLevel.Unhealthy
            in 201..300 -> AqiLevel.VeryUnhealthy
            in 301..500 -> AqiLevel.Hazardous
            else -> AqiLevel.Undefined
        }
    }

    fun getWeatherIcon(name: String): Drawable{
        val sanitizedName = "ic_$name"
        val resource = context.resources.getIdentifier(sanitizedName, "drawable", context.packageName)
        return context.resources.getDrawable(resource, null)
    }
}