package me.tbandawa.android.openweather.simplified.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            // required permissions state
            val locationPermissionState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            OpenWeathersimplifiedTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->

                    // if permissions granted, get co-ordinates, else request permissions
                    if (locationPermissionState.allPermissionsGranted) {
                        Text("Location permission Granted")

                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(context)
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                println("Latitude: ${location?.latitude} Longitude: ${location?.longitude}\n${location.toString()}")
                            }
                            .addOnFailureListener {
                                // request user to check settings
                            }

                    } else {
                        Column(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            if (locationPermissionState.shouldShowRationale) {
                                // request user to allow permissions
                                Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                                    Text("Request permission")
                                }
                            } else {
                                // allow permissions via app settings
                                Button(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", context.packageName, null)
                                            )
                                        )
                                    }
                                ) {
                                    Text("Open settings permission")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}