package me.tbandawa.android.openweather.simplified.data.extensions

import me.tbandawa.android.openweather.simplified.extensions.toDay
import me.tbandawa.android.openweather.simplified.extensions.toTemperature
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionsTest {
    
    @Test
    fun `is temperature in celcius`() {
        assertThat(45.0.toTemperature("C"), `is`("-228°"))
    }
    
    @Test
    fun `is temperature in fahrenheit`() {
        assertThat(45.0.toTemperature("F"), `is`("-196°F"))
    }

    @Test
    fun `is temperature in empty`() {
        assertThat(45.0.toTemperature("empty"), `is`(""))
    }

    @Test
    fun `is int to date`() {
        assertThat(1752418800.toDay(), `is`("Sunday"))
    }
}