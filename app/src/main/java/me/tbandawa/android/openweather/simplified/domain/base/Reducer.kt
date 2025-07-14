package me.tbandawa.android.openweather.simplified.domain.base

import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherResults

interface Reducer<STATE, T :Any> {
    fun reduce(result: OpenWeatherResults<T>, state: STATE): STATE
}