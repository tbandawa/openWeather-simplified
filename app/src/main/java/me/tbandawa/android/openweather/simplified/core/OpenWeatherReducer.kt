package me.tbandawa.android.openweather.simplified.core

import me.tbandawa.android.openweather.simplified.domain.model.Root

fun OpenWeatherResults<Root>.reduce(): OpenWeatherState<Root> {
    return when (this) {
        is OpenWeatherResults.Failure -> OpenWeatherState.Failure(data)
        is OpenWeatherResults.Loading -> OpenWeatherState.Loading
        is OpenWeatherResults.Success -> OpenWeatherState.Data(data)
    }
}