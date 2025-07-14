package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class CloudsResponse(
    val all: Long,
)