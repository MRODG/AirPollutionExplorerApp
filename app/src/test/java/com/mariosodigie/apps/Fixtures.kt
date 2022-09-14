package com.mariosodigie.apps

import com.mariosodigie.apps.airpollutionexplore.data.remote.dto.*

fun getPollutionInfoDtoFixture(): PollutionInfoDto{
    return PollutionInfoDto(data = getDataFixture())
}

fun getDataFixture(): Data {
    return Data(
        city = "test_city",
        state = "test_city",
        country = "test_city",
        current = getCurrentFixture()
    )
}

fun getCurrentFixture(): Current{
    return Current(
        weather = Weather(temperature = 31, humidity = 12, icon = "test_icon_url", wind = 55),
        Pollution(qualityIndex = 110)
    )
}