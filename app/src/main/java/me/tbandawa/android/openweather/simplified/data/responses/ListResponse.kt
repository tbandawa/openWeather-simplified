package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListResponse(
    val dt: Long,
    val main: MainResponse,
    val weather: List<WeatherResponse>,
    val clouds: CloudsResponse,
    val wind: WindResponse,
    val visibility: Long,
    val pop: Double,
    val rain: RainResponse? = null,
    val sys: SysResponse,
    @SerialName("dt_txt") val dtTxt: String,
)