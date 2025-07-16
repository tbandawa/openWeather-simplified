package me.tbandawa.android.openweather.simplified.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@Composable
fun LoadingScreen() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(45.dp)
                .testTag("spinner"),
            color = Color.Gray,
            trackColor = Color.LightGray,
            strokeWidth = 2.dp
        )
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    OpenWeathersimplifiedTheme {
        LoadingScreen()
    }
}