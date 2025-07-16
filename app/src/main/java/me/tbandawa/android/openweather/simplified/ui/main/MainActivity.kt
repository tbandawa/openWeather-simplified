package me.tbandawa.android.openweather.simplified.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import me.tbandawa.android.openweather.simplified.core.OpenWeatherIntent
import me.tbandawa.android.openweather.simplified.data.viewmodel.OpenWeatherViewModel
import me.tbandawa.android.openweather.simplified.service.LocationService
import me.tbandawa.android.openweather.simplified.ui.screens.LocationErrorScreen
import me.tbandawa.android.openweather.simplified.ui.screens.PermissionScreen
import me.tbandawa.android.openweather.simplified.ui.screens.RationaleScreen
import me.tbandawa.android.openweather.simplified.ui.screens.WeatherScreen
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            val viewModel: OpenWeatherViewModel = koinViewModel()

            // request location permissions state
            val locationPermissionState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            OpenWeathersimplifiedTheme {
                Column(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                ) {

                    // if permissions granted, get co-ordinates, else request permissions
                    if (locationPermissionState.allPermissionsGranted) {

                        // get last known location and update co-ordinates else null
                        LocationService(context).locationInfo.value?.let {
                            WeatherScreen(openWeatherState = viewModel.state.collectAsState().value) {
                                viewModel.handleIntent(OpenWeatherIntent.GetFiveDayWeather(it.latitude, it.longitude))
                            }
                            Text("LocationInfo: $it")
                        } ?: run {
                            LocationErrorScreen()
                        }

                    } else {

                        if (locationPermissionState.shouldShowRationale) {
                            // allow permissions via app settings
                            RationaleScreen()
                        } else {
                            // request user to allow permissions
                            PermissionScreen {
                                locationPermissionState.launchMultiplePermissionRequest()
                            }
                        }
                    }
                }
            }
        }
    }
}