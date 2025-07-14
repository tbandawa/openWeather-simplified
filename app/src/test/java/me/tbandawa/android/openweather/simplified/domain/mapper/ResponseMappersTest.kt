package me.tbandawa.android.openweather.simplified.domain.mapper

import me.tbandawa.android.openweather.simplified.base.BaseTest
import me.tbandawa.android.openweather.simplified.data.responses.RootResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ResponseMappersTest: BaseTest() {

    @Test
    fun `test five day weather mapper`() {
        val fiveDayWeatherForecastResponse = readJsonResponse<RootResponse>("five_day_weather.json")
        val fiveDayWeather = fiveDayWeatherMapper.mapToModel(fiveDayWeatherForecastResponse)
        assertThat(fiveDayWeatherForecastResponse.cod, `is`(fiveDayWeather.cod))
        assertThat(fiveDayWeatherForecastResponse.list.size, `is`(fiveDayWeather.list.size))
    }
}