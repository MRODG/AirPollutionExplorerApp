package com.mariosodigie.apps.airpollutionexplore.data.remote.dto
import com.squareup.moshi.Json

/*data class PollutionInfoDto(
    @field:Json(name = "city") var city: String = "",
    @field:Json(name = "state") var state: String = "",
    @field:Json(name = "country") var country: String = "",
    @field:Json(name = "aqius") var qualityIndex: Int = 0,
    @field:Json(name = "tp") var temperature: Int = 0,
    @field:Json(name = "hu") var humidity: Int = 0,
    @field:Json(name = "ic") var icon: String = "",
    @field:Json(name = "ws") var wind: Int = 0,
)*/

data class Pollution(
    @field:Json(name = "aqius")val qualityIndex: Int
)

data class Weather(
    @field:Json(name = "tp") var temperature: Int = 0,
    @field:Json(name = "hu") var humidity: Int = 0,
    @field:Json(name = "ic") var icon: String = "",
    @field:Json(name = "wd") var wind: Int = 0,
)
data class Current(
    @field:Json(name = "weather")val weather: Weather,
    @field:Json(name = "pollution")val pollution:Pollution
)

data class Data(
    @field:Json(name = "city") var city: String = "",
    @field:Json(name = "state") var state: String = "",
    @field:Json(name = "country") var country: String = "",
    @field:Json(name = "current")val current: Current
)
data class PollutionInfoDto(
    @field:Json(name = "data") var data: Data,
)
/*

{
    "status": "success",
    "data": {
        "city": "Kensington",
        "state": "England",
        "country": "United Kingdom",
        "location": {
            "type": "Point",
            "coordinates": [
                -0.192964,
                51.490536
            ]
        },
        "current": {
            "weather": {
                "ts": "2020-02-07T11:00:00.000Z",
                "tp": 5,
                "pr": 1020,
                "hu": 81,
                "ws": 3.1,
                "wd": 90,
                "ic": "01d"
            },
            "pollution": {
                "ts": "2020-02-07T11:00:00.000Z",
                "aqius": 98,
                "mainus": "p2",
                "aqicn": 49,
                "maincn": "p2"
            }
        }
    }
}
 */
