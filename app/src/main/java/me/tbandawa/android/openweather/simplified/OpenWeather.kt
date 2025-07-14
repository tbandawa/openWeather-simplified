package me.tbandawa.android.openweather.simplified

import android.app.Application
import me.tbandawa.android.openweather.simplified.di.initKoin
import org.koin.android.ext.koin.androidContext

class OpenWeather: Application() {

    override fun onCreate() {
        super.onCreate()

        // start koin
        initKoin {
            androidContext(this@OpenWeather)
        }
    }
}