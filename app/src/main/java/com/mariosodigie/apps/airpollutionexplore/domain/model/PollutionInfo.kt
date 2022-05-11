package com.mariosodigie.apps.airpollutionexplore.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PollutionInfo(
    val city: String?,
    val state: String?,
    val country: String?,
    val qualityIndex: Int?,
    val temperature: Int?,
    val humidity: Int?,
    val icon: String?,
    val wind: Int?
): Parcelable


