package me.tbandawa.android.openweather.simplified.data.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.tbandawa.android.openweather.simplified.domain.base.BaseViewModel
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherEffect
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherIntent
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherState
import me.tbandawa.android.openweather.simplified.domain.core.reduce
import me.tbandawa.android.openweather.simplified.domain.model.Root
import me.tbandawa.android.openweather.simplified.domain.repo.OpenWeatherRepo

class OpenWeatherViewModel(
    private val repository: OpenWeatherRepo
): BaseViewModel<OpenWeatherState<Root>, OpenWeatherIntent, OpenWeatherEffect>() {

    override fun createInitialState(): OpenWeatherState<Root> = OpenWeatherState.Loading

    override fun handleIntent(intent: OpenWeatherIntent) {
        when (intent) {
            is OpenWeatherIntent.GetFiveDayWeather -> {
                viewModelScope.launch {
                    getFiveDayWeather(lat = intent.lat, lon = intent.lon)
                }
            }
            is OpenWeatherIntent.Error -> {}
        }
    }

    private suspend fun getFiveDayWeather(lat: Double, lon: Double) {
        repository.getFiveDayWeather(lat, lon).collect { results ->
            _state.value = results.reduce()
        }
    }
}
