package me.tbandawa.android.openweather.simplified.base

import kotlinx.serialization.json.Json
import me.tbandawa.android.openweather.simplified.domain.mapper.FiveDayWeatherResponseMapper
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class BaseTest {

    protected lateinit var fiveDayWeatherMapper: FiveDayWeatherResponseMapper

    @Before
    fun testsSetup() {
        fiveDayWeatherMapper = FiveDayWeatherResponseMapper()
    }

    protected inline fun <reified T : Any> readJsonResponse(fileName: String) : T {
        val fileContent = this::class.java.classLoader!!.getResource(fileName).readText()
        return Json.decodeFromString<T>(fileContent)
    }
}