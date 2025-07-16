package me.tbandawa.android.openweather.simplified.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import me.tbandawa.android.openweather.simplified.core.OpenWeatherIntent
import me.tbandawa.android.openweather.simplified.data.viewmodel.OpenWeatherViewModel
import me.tbandawa.android.openweather.simplified.domain.model.Coord
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

            // co-ordinates state
            val isCoordinates = remember { mutableStateOf<Coord?>(null) }

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
                        LocationServices.getFusedLocationProviderClient(context).lastLocation
                            .addOnSuccessListener { location: Location? ->
                                location?.let {
                                    isCoordinates.value = Coord(it.latitude, it.longitude)
                                } ?.run {
                                    isCoordinates.value = null
                                }
                            }
                            .addOnFailureListener {
                                isCoordinates.value = null
                            }

                        // if co-ordinates are not null, show weather screen else show rationale screen
                        isCoordinates.value?.let { coord ->
                            WeatherScreen(openWeatherState = viewModel.state.collectAsState().value) {
                                viewModel.handleIntent(OpenWeatherIntent.GetFiveDayWeather(coord.lat, coord.lon))
                            }
                        }?.run {
                            RationaleScreen()
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