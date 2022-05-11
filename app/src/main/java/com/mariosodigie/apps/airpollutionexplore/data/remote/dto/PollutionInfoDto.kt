package com.mariosodigie.apps.airpollutionexplore.data.dto
import com.squareup.moshi.Json
class PollutionInfoDto {
}



data class PollutionInfoDto(
    @field:Json(name = "city") val symbol: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Industry") val industry: String?,
)

data class Weather(val temperature: Int,
                   val humidity: Int,
                   val icon: String,
                   val wind: Int)

data class Pollution(val qualityIndex: Int)

data class PollutionResponse(

    val city: String,
    val state: String,
    val country: String,
    val weather: Weather,
    val pollution:Pollution
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
