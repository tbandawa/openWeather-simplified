package me.tbandawa.android.openweather.simplified.domain.core

import me.tbandawa.android.openweather.simplified.domain.model.Error

sealed class OpenWeatherResults<out M> {
    data class Success<out M>(val data: M): OpenWeatherResults<M>()
    data class Failure<out M>(val data: Error? = null): OpenWeatherResults<M>()
    data object Loading: OpenWeatherResults<Nothing>()
}