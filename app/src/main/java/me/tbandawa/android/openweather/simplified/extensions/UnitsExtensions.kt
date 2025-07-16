package me.tbandawa.android.openweather.simplified.extensions

import kotlin.math.roundToInt
import java.text.SimpleDateFormat
import java.util.*

fun Double.toTemperature(unit: String) : String {
    return when (unit) {
        "C" -> {
            var celcius = this - 273.15
            "${celcius.roundToInt()}°"
        }
        "F" -> {
            var fahrenheit = (9/5*(this - 273)) + 32
            "${fahrenheit.roundToInt()}°F"
        }
        else -> {""}
    }
}

fun Int.toDay() : String {
    val seconds = this*1000L
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = seconds
    val formatter = SimpleDateFormat("EEEE")
    return formatter.format(calendar.time)
}

fun Int.toDate() : String {
    val seconds = this*1000L
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = seconds
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return formatter.format(calendar.time)
}