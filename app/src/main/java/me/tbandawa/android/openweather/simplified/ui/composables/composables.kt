package me.tbandawa.android.openweather.simplified.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import me.tbandawa.android.openweather.simplified.domain.model.Clouds
import me.tbandawa.android.openweather.simplified.domain.model.List
import me.tbandawa.android.openweather.simplified.domain.model.Main
import me.tbandawa.android.openweather.simplified.domain.model.Sys
import me.tbandawa.android.openweather.simplified.domain.model.Weather
import me.tbandawa.android.openweather.simplified.domain.model.Wind
import me.tbandawa.android.openweather.simplified.extensions.toDay
import me.tbandawa.android.openweather.simplified.extensions.toTemperature
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenWeatherTopBar(
    contentColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "5 Day Forecast",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                titleContentColor = contentColor
            ),
            modifier = Modifier
                .background(color = Color.Transparent)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(color = contentColor)
        )
    }
}

@Composable
fun WeatherItem(
    weather: List
) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .fillMaxWidth()
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Text(
                text = weather.dt.toInt().toDay(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png",
                    contentDescription = weather.weather[0].description,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(45.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = weather.main.temp.toTemperature("C"),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun OpenWeatherTopBarPreview() {
    OpenWeathersimplifiedTheme {
        OpenWeatherTopBar(
            contentColor = Color.Black
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun WeatherItemPreview() {
    val weather = List(
        dt = 1752591600,
        main = Main(292.52, 291.77, 290.16, 292.52, 1020, 1020, 832, 48, 2.36),
        weather = listOf(Weather(800, "Clear", "clear sky", "01d")),
        clouds = Clouds(1L),
        wind = Wind(2.87, 319, 3.55),
        visibility = 10000,
        pop = 12L,
        sys = Sys("d"),
        dtTxt = "2025-07-15 15:00:00"
    )
    OpenWeathersimplifiedTheme {
        WeatherItem(weather)
    }
}