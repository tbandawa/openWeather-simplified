package me.tbandawa.android.openweather.simplified.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@Composable
fun ErrorScreen(
    message: String,
    retry: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 55.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        retry.invoke()
                    },
                text = "Retry",
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Composable
@Preview
fun ErrorScreenPreview() {
    OpenWeathersimplifiedTheme {
        ErrorScreen(message = "Something went wrong") {}
    }
}