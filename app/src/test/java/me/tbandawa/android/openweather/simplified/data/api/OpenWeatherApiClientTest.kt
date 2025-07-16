package me.tbandawa.android.openweather.simplified.data.api

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import me.tbandawa.android.openweather.simplified.base.BaseTest
import me.tbandawa.android.openweather.simplified.extensions.toDate
import me.tbandawa.android.openweather.simplified.extensions.toDay
import me.tbandawa.android.openweather.simplified.extensions.toTemperature
import org.hamcrest.CoreMatchers.containsString
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

    @Test
    fun `weather forecast is five days`() = runBlocking {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        val response = openWeatherApiClient.fetchFiveDayWeather(1.0, 2.0)
        assertThat(response.cod, `is`("200"))
        assertThat(response.list.size, `is`(40))
        assertThat(response.city.name, `is`("Johannesburg"))
    }

    @Test
    fun `weather forecast converters`() = runBlocking {
        enqueueResponse("five_day_weather.json")
        openWeatherApiClient = OpenWeatherApiClient(mockEngine)
        val response = openWeatherApiClient.fetchFiveDayWeather(1.0, 2.0)
        val todayWeather = response.list.first()
        assertThat(todayWeather.dt.toInt().toDay(), `is`("Sunday"))
        assertThat(todayWeather.dt.toInt().toDate(), `is`("2025-07-13 17:00:00"))
        assertThat(todayWeather.main.temp.toTemperature("C"), `is`("16°"))
        assertThat(todayWeather.main.temp.toTemperature("F"), `is`("48°F"))
    }

    @Test
    fun `five day weather api request is failure`() {
        try {
            enqueueResponse("error.json", HttpStatusCode.Unauthorized)
            openWeatherApiClient = OpenWeatherApiClient(mockEngine)
            runBlocking {
                openWeatherApiClient.fetchFiveDayWeather(1.0, 2.0)
            }
        } catch (e: ClientRequestException) {
            assertThat(e.response.status.value, `is`(401))
            assertThat(e.message, containsString("Invalid API key"))
        }
    }
}