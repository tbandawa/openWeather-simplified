package me.tbandawa.android.openweather.simplified.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import me.tbandawa.android.openweather.simplified.R
import me.tbandawa.android.openweather.simplified.core.OpenWeatherState
import me.tbandawa.android.openweather.simplified.domain.model.Error
import me.tbandawa.android.openweather.simplified.domain.model.Root
import me.tbandawa.android.openweather.simplified.ui.composables.OpenWeatherTopBar
import me.tbandawa.android.openweather.simplified.ui.composables.WeatherItem
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@Composable
fun WeatherScreen(
    openWeatherState: OpenWeatherState<Root>,
    handleIntent: () -> Unit
) {

    // track if the data is loaded
    val isLoaded = remember { mutableStateOf(false) }

    // hold the background resource id
    val bgResourceId = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        handleIntent.invoke()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // show the background image only when the data is loaded
        if (isLoaded.value) {
            Image(
                painter = painterResource(id = bgResourceId.intValue),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                OpenWeatherTopBar(
                    contentColor = if (isLoaded.value) Color.White else Color.Black
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
            ) {

                // show the appropriate composable based on the state
                when (openWeatherState) {
                    is OpenWeatherState.Loading -> {
                        LoadingScreen()
                    }
                    is OpenWeatherState.Data -> {
                        isLoaded.value = true
                        val root = (openWeatherState as OpenWeatherState.Data<*>).data as Root
                        bgResourceId.intValue = getBackGround(root.list[0].weather[0].main)
                        Column {
                            for (i in 0 until root.list.size step 8) {
                                WeatherItem(root.list[i])
                            }
                        }
                    }
                    is OpenWeatherState.Failure -> {
                        val error = (openWeatherState as OpenWeatherState.Failure<*>).data as Error
                        ErrorScreen(error.message) {
                            // invoke the intent again on error
                            handleIntent.invoke()
                        }
                    }
                }
            }
        }
    }
}

/*
 *  function returns the background resource id based on the weather type.
 *  https://openweathermap.org/weather-conditions
 */
fun getBackGround(type: String): Int {
    return when (type.lowercase()) {
        "clouds" -> {
            R.drawable.bg_cloudy
        }
        "clear" -> {
            R.drawable.bg_clear
        }
        "mist", "smoke", "haze", "dust", "fog", "sand", "ash", "squall", "tornado" -> {
            R.drawable.bg_mist
        }
        "snow" -> {
            R.drawable.bg_snow
        }
        "rain", "drizzle", "thunderstorm" -> {
            R.drawable.bg_rainy
        }
        else -> { R.drawable.bg_forest }
    }
}

@Composable
@Preview
fun WeatherScreenPreview() {
    OpenWeathersimplifiedTheme {
        WeatherScreen(
            openWeatherState = OpenWeatherState.Loading
        ) {}
    }
}