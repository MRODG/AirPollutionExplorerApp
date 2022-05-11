package com.mariosodigie.apps.airpollutionexplore.data.mapper

import com.mariosodigie.apps.airpollutionexplore.data.remote.dto.PollutionInfoDto
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo

fun PollutionInfoDto.toPollutionInfo(): PollutionInfo {
    return PollutionInfo(
        city = data.city,
        state = data.state,
        country = data.country,
        qualityIndex = data.current.pollution.qualityIndex,
        temperature = data.current.weather.temperature,
        humidity = data.current.weather.humidity,
        icon = data.current.weather.icon,
        wind = data.current.weather.wind
    )
}