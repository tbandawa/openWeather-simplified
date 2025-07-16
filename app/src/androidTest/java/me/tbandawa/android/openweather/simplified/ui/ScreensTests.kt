package me.tbandawa.android.openweather.simplified.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import me.tbandawa.android.openweather.simplified.ui.screens.ErrorScreen
import me.tbandawa.android.openweather.simplified.ui.screens.LoadingScreen
import me.tbandawa.android.openweather.simplified.ui.screens.PermissionScreen
import me.tbandawa.android.openweather.simplified.ui.screens.RationaleScreen
import me.tbandawa.android.openweather.simplified.ui.theme.OpenWeathersimplifiedTheme
import org.junit.Rule
import org.junit.Test

class ScreensTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorScreen() {
        composeTestRule.setContent {
            OpenWeathersimplifiedTheme {
                ErrorScreen("Error") { }
            }
        }
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry")
            .assertIsDisplayed()
            .assert(hasClickAction())
            .performClick()
    }

    @Test
    fun testLoadingScreen() {
        composeTestRule.setContent {
            OpenWeathersimplifiedTheme {
                LoadingScreen()
            }
        }
        composeTestRule.onNode(hasTestTag("spinner"), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Test
    fun testPermissionsScreen() {
        composeTestRule.setContent {
            OpenWeathersimplifiedTheme {
                PermissionScreen { }
            }
        }
        composeTestRule.onNodeWithText("Location Access").assertIsDisplayed()
        composeTestRule.onNodeWithText("Allow")
            .assertIsDisplayed()
            .assert(hasClickAction())
            .performClick()
        composeTestRule.onNodeWithText("Exit")
            .assertIsDisplayed()
            .assert(hasClickAction())
            .performClick()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Test
    fun testRationaleScreen() {
        composeTestRule.setContent {
            OpenWeathersimplifiedTheme {
                RationaleScreen()
            }
        }
        composeTestRule.onNodeWithText("Location access permission denied.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Open Settings")
            .assertIsDisplayed()
            .assert(hasClickAction())
        composeTestRule.onNodeWithText("Exit")
            .assertIsDisplayed()
            .assert(hasClickAction())
            .performClick()
    }
}