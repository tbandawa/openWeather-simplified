package me.tbandawa.android.openweather.simplified.domain.model

data class City(
    var id: Long,
    var name: String,
    var coord: Coord,
    var country: String,
    var population: Long,
    var timezone: Long,
    var sunrise: Long,
    var sunset: Long,
)