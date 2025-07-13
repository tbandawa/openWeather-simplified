package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainResponse(
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    val pressure: Long,
    @SerialName("sea_level") val seaLevel: Long,
    @SerialName("grnd_level") val grndLevel: Long,
    val humidity: Long,
    @SerialName("temp_kf") val tempKf: Double,
)