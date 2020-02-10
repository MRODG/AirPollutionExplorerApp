package com.mariosodigie.apps.airpollutionexplore

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mariosodigie.apps.airpollutionexplore.utils.runOnMainThread
import com.mariosodigie.apps.airpollutionexplore.api.ApiError
import com.mariosodigie.apps.airpollutionexplore.api.ServiceCallback


data class ServiceError(val title: String?, val message: String, val apiError: ApiError = ApiError.Generic)

abstract class BaseViewModel(protected val context: Context) : ViewModel() {

    val requestInProgress = MutableLiveData<Boolean>()
    val serviceError = MutableLiveData<ServiceError>()

    protected inner class ServCallback<T>(private val handler: (T) -> Unit) :
        ServiceCallback<T> {

        init {
            requestInProgress.value = true
        }

        override fun onSuccess(response: T) = runOnMainThread {
            requestInProgress.value = false
            handler(response)
        }

        override fun onError(error: ApiError) = runOnMainThread {
            requestInProgress.value = false
            serviceError.value = ServiceError(context.getString(error.title), context.getString(error.message), error)
        }

        override fun onError(error: Throwable) = onError(ApiError.Generic)
    }
}