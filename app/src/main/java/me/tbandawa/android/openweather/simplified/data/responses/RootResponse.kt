package me.tbandawa.android.openweather.simplified.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class RootResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListResponse>,
    val city: CityResponse,
)