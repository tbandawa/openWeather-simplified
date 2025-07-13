package me.tbandawa.android.openweather.simplified.domain.model

data class Weather(
    var id: Long,
    var main: String,
    var description: String,
    var icon: String,
)