package me.tbandawa.android.openweather.simplified.domain.model

data class List(
    var dt: Long,
    var main: Main,
    var weather: kotlin.collections.List<Weather>,
    var clouds: Clouds,
    var wind: Wind,
    var visibility: Long,
    var pop: Long,
    var sys: Sys,
    var dtTxt: String,
)