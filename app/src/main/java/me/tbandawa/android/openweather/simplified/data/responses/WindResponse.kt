package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class WindResponse(
    val speed: Double,
    val deg: Long,
    val gust: Double,
)