package me.tbandawa.android.openweather.simplified.domain.mapper

interface ResponseMapper<R, M> {
    fun mapToModel(response: R): M
}