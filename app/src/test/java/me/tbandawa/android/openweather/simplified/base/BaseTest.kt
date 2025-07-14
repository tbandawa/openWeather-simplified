package me.tbandawa.android.openweather.simplified.base

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import me.tbandawa.android.openweather.simplified.data.api.OpenWeatherApiClient
import me.tbandawa.android.openweather.simplified.data.repo.OpenWeatherRepoImpl
import me.tbandawa.android.openweather.simplified.data.viewmodel.OpenWeatherViewModel
import me.tbandawa.android.openweather.simplified.data.mapper.ResponseMapperImpl
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {

    protected lateinit var fiveDayWeatherMapper: ResponseMapperImpl

    protected lateinit var openWeatherRepo: OpenWeatherRepoImpl

    protected lateinit var openWeatherApiClient: OpenWeatherApiClient

    protected lateinit var openWeatherViewModel: OpenWeatherViewModel

    protected lateinit var mockEngine: MockEngine

    @Before
    fun testsSetup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        fiveDayWeatherMapper = ResponseMapperImpl()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    protected fun enqueueResponse(
        fileName: String,
        statusCode: HttpStatusCode = HttpStatusCode.OK
    ) {
        mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(this::class.java.classLoader!!.getResource(fileName).readText()),
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }

    protected inline fun <reified T : Any> readJsonResponse(fileName: String) : T {
        val fileContent = stringResponse(fileName)
        return Json.decodeFromString<T>(fileContent)
    }

    protected fun BaseTest.stringResponse(fileName: String): String =
        this::class.java.classLoader!!.getResource(fileName).readText()
}