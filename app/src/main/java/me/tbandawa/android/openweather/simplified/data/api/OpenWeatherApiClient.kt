package me.tbandawa.android.openweather.simplified.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tbandawa.android.openweather.simplified.data.responses.RootResponse

class OpenWeatherApiClient(httpClientEngine: HttpClientEngine) {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5"
        const val API_KEY = "dfc3d17632993a8fd49021d3c3d20f96"
    }

    val httpClient = HttpClient(httpClientEngine) {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun fetchFiveDayWeather(lat: Double, lon: Double): RootResponse {
        return httpClient.get {
            url("${BASE_URL}/forecast")
            parameter("appid", API_KEY)
            parameter("lat", lat)
            parameter("lon", lon)
        }.body()
    }

}
