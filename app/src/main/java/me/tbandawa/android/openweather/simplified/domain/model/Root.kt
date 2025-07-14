package me.tbandawa.android.openweather.simplified.domain.model

data class Root(
    var cod: String,
    var message: Long,
    var cnt: Long,
    var list: kotlin.collections.List<List>,
    var city: City,
)