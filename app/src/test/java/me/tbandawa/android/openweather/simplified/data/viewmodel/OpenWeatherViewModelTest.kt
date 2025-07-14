package me.tbandawa.android.openweather.simplified.data.viewmodel

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.tbandawa.android.openweather.simplified.base.BaseTest
import me.tbandawa.android.openweather.simplified.data.api.OpenWeatherApiClient
import me.tbandawa.android.openweather.simplified.data.repo.OpenWeatherRepoImpl
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherIntent
import me.tbandawa.android.openweather.simplified.domain.core.OpenWeatherState
import me.tbandawa.android.openweather.simplified.domain.model.Root
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class OpenWeatherViewModelTest: BaseTest() {

    @Test
    fun `test initial state`() {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        openWeatherRepo = OpenWeatherRepoImpl(
            openWeatherApiClient,
            fiveDayWeatherMapper,
            Dispatchers.Unconfined
        )
        openWeatherViewModel = OpenWeatherViewModel(openWeatherRepo)
        assertThat(openWeatherViewModel.createInitialState(), `is`(OpenWeatherState.Loading))
    }

    @Test
    fun `test get weather intent`() = runBlocking {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        openWeatherRepo = OpenWeatherRepoImpl(
            openWeatherApiClient,
            fiveDayWeatherMapper,
            Dispatchers.Unconfined
        )
        openWeatherViewModel = OpenWeatherViewModel(openWeatherRepo)
        openWeatherViewModel.handleIntent(OpenWeatherIntent.GetFiveDayWeather(1.0, 2.0))
        delay(500.milliseconds)
        val result = openWeatherViewModel.state.value as OpenWeatherState.Data<Root>
        assertThat(result.data.city.name, `is`("Johannesburg"))
    }

    @Test
    fun `test get weather failure`() = runBlocking {
        enqueueResponse("error.json", HttpStatusCode.Unauthorized)
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        openWeatherRepo = OpenWeatherRepoImpl(
            openWeatherApiClient,
            fiveDayWeatherMapper,
            Dispatchers.Unconfined
        )
        openWeatherViewModel = OpenWeatherViewModel(openWeatherRepo)
        openWeatherViewModel.handleIntent(OpenWeatherIntent.GetFiveDayWeather(1.0, 2.0))
        delay(500.milliseconds)
        val result = openWeatherViewModel.state.value as OpenWeatherState.Failure<*>
        assertThat(result.data?.message, `is`("Unauthorized"))
    }
}