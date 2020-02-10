package com.mariosodigie.apps.airpollutionexplore.explorefeature

import androidx.annotation.DrawableRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.mariosodigie.apps.airpollutionexplore.R

enum class AqiLevel(@StringRes val title: Int, @ColorRes val colour: Int) {

    Good(R.string.pollution_good , R.color.pollution_good),
    Moderate(R.string.pollution_moderate , R.color.pollution_moderate),
    UnhealthySensitive(R.string.pollution_unhealthy_for_sensitive , R.color.pollution_unhealthy_for_sensitive),
    Unhealthy(R.string.pollution_unhealthy , R.color.pollution_unhealthy),
    VeryUnhealthy(R.string.pollution_very_unhealthy , R.color.pollution_very_unhealthy),
    Hazardous(R.string.pollution_hazardous, R.color.pollution_hazardous),
    Undefined(R.string.pollution_undefined, R.color.off_white)
}
