package com.mariosodigie.apps.airpollutionexplore.network

import android.app.AlertDialog
import androidx.lifecycle.LiveData

interface OnNetworkErrorListener {
    var alertDialog: AlertDialog?
    fun addErrorSource(source: LiveData<NetworkError>)
    fun showErrorDialog(error: NetworkError)
}