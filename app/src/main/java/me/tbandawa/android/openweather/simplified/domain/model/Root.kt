package me.tbandawa.android.openweather.simplified.domain.model

data class Root(
    var cod: String,
    var message: Long,
    var cnt: Long,
    var list: kotlin.collections.List<List>,
    var city: City,
) {

    // returns five day interval weather
    fun getFiveDayInterval(): kotlin.collections.List<List> {
        return this.list.filterIndexed { index, _ ->
            index % 8 == 0
        }
    }
}