package me.tbandawa.android.openweather.simplified.data.repo

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.tbandawa.android.openweather.simplified.base.BaseTest
import me.tbandawa.android.openweather.simplified.data.api.OpenWeatherApiClient
import me.tbandawa.android.openweather.simplified.core.OpenWeatherResults
import me.tbandawa.android.openweather.simplified.domain.model.Root
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class OpenWeatherRepoTest: BaseTest() {

    @Test
    fun `test fetch five day weather success`() = runBlocking {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        openWeatherRepo = OpenWeatherRepoImpl(
            openWeatherApiClient,
            fiveDayWeatherMapper,
            Dispatchers.Unconfined
        )
        openWeatherRepo.getFiveDayWeather(1.0, 2.0).collect { value: OpenWeatherResults<Root> ->
            if(value is OpenWeatherResults.Success) {
                assertThat(value.data.city.name, `is`("Johannesburg"))
            }
        }
    }

    @Test
    fun `test fetch five day weather error`() = runBlocking {
        enqueueResponse("error.json", HttpStatusCode.Unauthorized)
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        openWeatherRepo = OpenWeatherRepoImpl(
            openWeatherApiClient,
            fiveDayWeatherMapper,
            Dispatchers.Unconfined
        )
        openWeatherRepo.getFiveDayWeather(1.0, 2.0).collect { value: OpenWeatherResults<Root> ->
            if(value is OpenWeatherResults.Failure) {
                assertThat(value.data?.message, `is`("Unauthorized"))
            }
        }
    }
}