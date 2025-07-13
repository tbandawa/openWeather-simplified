package me.tbandawa.android.openweather.simplified.domain.repo

import kotlinx.coroutines.flow.Flow
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherResults
import me.tbandawa.android.openweather.simplified.domain.model.Root

interface OpenWeatherRepo {
    suspend fun getFiveDayWeather(lat: Double, lon: Double): Flow<OpenWeatherResults<Root>>
}

