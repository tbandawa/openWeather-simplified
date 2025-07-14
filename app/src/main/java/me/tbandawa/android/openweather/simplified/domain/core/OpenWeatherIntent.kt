package me.tbandawa.android.openweather.simplified.domain.core

import me.tbandawa.android.openweather.simplified.domain.base.Intent

sealed class OpenWeatherIntent : Intent {
    data class GetFiveDayWeather(val lat: Double, val lon: Double): OpenWeatherIntent()
}