package me.tbandawa.android.openweather.simplified.data.api

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import me.tbandawa.android.openweather.simplified.base.BaseTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class OpenWeatherApiClientTest: BaseTest() {

    @Test
    fun `five day weather api request is success`() = runBlocking {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        val response = openWeatherApiClient.fetchFiveDayWeather(1.0, 2.0)
        assertThat(response.cod, `is`("200"))
        assertThat(response.list.size, `is`(40))
        assertThat(response.city.name, `is`("Johannesburg"))
    }
}