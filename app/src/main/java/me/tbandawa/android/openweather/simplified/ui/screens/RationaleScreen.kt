package me.tbandawa.android.openweather.simplified.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme

@ExperimentalPermissionsApi
@Composable
fun RationaleScreen() {

    Surface(color = MaterialTheme.colorScheme.background) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, bottom = 50.dp, end = 20.dp)
        ) {

            val context = LocalContext.current

            val (titleLayout, descriptionLayout, allowButton, cancelButton) = createRefs()

            // Title text
            Text(
                text = "Location access permission denied.",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .constrainAs(titleLayout) {
                        bottom.linkTo(descriptionLayout.top)
                        start.linkTo(parent.start)
                    }
                    .height(IntrinsicSize.Min)
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
            )

            // Rationale text
            Text(
                text = "openWeather needs access location to know where you are. Please grant access on the Settings screen.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .constrainAs(descriptionLayout) {
                        start.linkTo(parent.start)
                        bottom.linkTo(allowButton.top)
                        bottom.linkTo(cancelButton.top)
                    }
                    .width(300.dp)
                    .height(IntrinsicSize.Max)
                    .padding(bottom = 10.dp)
            )

            // Open app settings button
            Button(
                onClick = {
                    context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                    )
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .constrainAs(allowButton) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Open Settings",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Exit button
            Button(
                onClick = {
                    (context as ComponentActivity).finish()
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .constrainAs(cancelButton) {
                        start.linkTo(allowButton.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Exit",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun RationaleContentPreview() {
    OpenWeathersimplifiedTheme {
        RationaleScreen()
    }
}