package me.tbandawa.android.openweather.simplified.domain.model

data class Main(
    var temp: Double,
    var feelsLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Long,
    var seaLevel: Long,
    var grndLevel: Long,
    var humidity: Long,
    var tempKf: Double,
)