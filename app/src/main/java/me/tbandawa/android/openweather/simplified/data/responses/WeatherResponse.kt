package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)