package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class CoordResponse(
    val lat: Double,
    val lon: Double,
)