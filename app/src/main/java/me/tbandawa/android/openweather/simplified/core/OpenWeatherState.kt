package me.tbandawa.android.openweather.simplified.core

import me.tbandawa.android.openweather.simplified.domain.model.Error
import me.tbandawa.android.openweather.simplified.domain.base.State

sealed class OpenWeatherState<out M>: State {
    data class Data<out M>(val data: M): OpenWeatherState<M>()
    data class Failure<out M>(val data: Error? = null): OpenWeatherState<M>()
    data object Loading: OpenWeatherState<Nothing>()
}