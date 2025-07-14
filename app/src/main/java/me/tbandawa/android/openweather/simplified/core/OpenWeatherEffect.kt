package me.tbandawa.android.openweather.simplified.core

import me.tbandawa.android.openweather.simplified.domain.base.Effect

sealed class OpenWeatherEffect : Effect {
    data class Error(val error: String): OpenWeatherEffect()
}