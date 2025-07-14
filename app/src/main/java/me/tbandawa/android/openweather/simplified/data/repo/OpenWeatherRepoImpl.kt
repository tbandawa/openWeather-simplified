package me.tbandawa.android.openweather.simplified.data.repo

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException
import me.tbandawa.android.openweather.simplified.data.api.OpenWeatherApiClient
import me.tbandawa.android.openweather.simplified.core.OpenWeatherResults
import me.tbandawa.android.openweather.simplified.data.mapper.ResponseMapperImpl
import me.tbandawa.android.openweather.simplified.domain.model.Error
import me.tbandawa.android.openweather.simplified.domain.repo.OpenWeatherRepo

class OpenWeatherRepoImpl(
    private val apiClient: OpenWeatherApiClient,
    private val responseMapper: ResponseMapperImpl,
    private val coroutineDispatcher: CoroutineDispatcher,
): OpenWeatherRepo {

    override suspend fun getFiveDayWeather(lat: Double, lon: Double) = flow {
        emit(OpenWeatherResults.Loading)
        emit(handleApiCall {
            responseMapper.mapToModel(apiClient.fetchFiveDayWeather(lat, lon))
        })
    }.flowOn(coroutineDispatcher)
}

suspend fun <T> handleApiCall(
    apiCall: suspend () -> T
): OpenWeatherResults<T> {
    return try {
        val response = apiCall()
        OpenWeatherResults.Success(response)
    } catch (e: ResponseException) {
        OpenWeatherResults.Failure(Error(e.response.status.value, e.response.status.description))
    } catch (e: ClientRequestException) {
        OpenWeatherResults.Failure(Error(e.response.status.value, e.response.status.description))
    } catch (e: ServerResponseException) {
        OpenWeatherResults.Failure(Error(e.response.status.value, e.response.status.description))
    } catch (e: IOException) {
        OpenWeatherResults.Failure(Error(500, "Server unreachable. Please check your internet connection and try again"))
    } catch (e: Exception) {
        OpenWeatherResults.Failure(Error(500, "Unexpected error occurred. Try again"))
    }
}
