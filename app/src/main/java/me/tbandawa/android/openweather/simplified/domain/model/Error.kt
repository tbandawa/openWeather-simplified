package me.tbandawa.android.openweather.simplified.domain.model

data class Error (
    val status: Int,
    val error : String,
    val detail : String
)