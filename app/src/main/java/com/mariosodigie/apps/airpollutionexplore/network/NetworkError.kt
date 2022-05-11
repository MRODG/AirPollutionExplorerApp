package com.mariosodigie.apps.airpollutionexplore.network

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mariosodigie.apps.airpollutionexplore.R

enum class NetworkError(@StringRes val title: Int, @StringRes val message: Int, @DrawableRes val icon: Int? = null) {

    Generic(R.string.error_title_generic, R.string.error_message_generic),
    PhoneOffline(R.string.phone_error_title, R.string.phone_error_message),
    OutRegion(R.string.error_title_region, R.string.error_message_region)
}