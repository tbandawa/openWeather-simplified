package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainResponse(
    @SerialName("3h") val threeHours: Double,
)
