package com.mariosodigie.apps.airpollutionexplore.explorefeature

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.BuildConfig
import com.mariosodigie.apps.airpollutionexplore.api.ApiError
import com.mariosodigie.apps.airpollutionexplore.api.ServiceCallback
import com.mariosodigie.apps.airpollutionexplore.explorefeature.api.ApiService
import com.mariosodigie.apps.airpollutionexplore.explorefeature.model.Pollution
import com.mariosodigie.apps.airpollutionexplore.explorefeature.model.PollutionResponse
import com.mariosodigie.apps.airpollutionexplore.explorefeature.model.Weather
import com.mariosodigie.apps.airpollutionexplore.utils.ConnectivityCheck
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject

/**
 * Performs the calls to our API and exposes them to concerned ViewModels.
 */
class ExplorerService(private val apiServe: ApiService,
                      private val connectivityCheck: ConnectivityCheck){

     fun jsonParser(response: String?): PollutionResponse{
        val jsonObject = JSONObject(response)
        val data = jsonObject.getJSONObject("data")

        val weather = data.getJSONObject("current")
            .getJSONObject("weather")

        val pollution = data.getJSONObject("current")
            .getJSONObject("pollution")

        return PollutionResponse(
         data.getString("city"),
         data.getString("state"),
         data.getString("country"),
         Weather(
             weather.getInt("tp"),
             weather.getInt("hu"),
             weather.getString("ic"),
             weather.getInt("ws")
         ),
         Pollution(pollution.getInt("aqius"))
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun makeCall(location: LatLng, callback: ServiceCallback<String?>){

        if (!connectivityCheck.isConnectedToNetwork()) {
            callback.onError(ApiError.PhoneOffline)
            return
        }
        apiServe.getResponseByLocation(location.latitude, location.longitude, BuildConfig.EXPLORE_API_KEY)
                .enqueue( object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.onError(t)
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(!response.isSuccessful){
                            if(response.code()==404) callback.onError(ApiError.OutRegion)
                            else callback.onError(Exception(response.errorBody()?.string()))
                        }
                        else{
                            callback.onSuccess(response.body()?.string())
                        }
                    }
                })
    }
}