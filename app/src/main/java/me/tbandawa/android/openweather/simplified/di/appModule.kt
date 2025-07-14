package me.tbandawa.android.openweather.simplified.di

import io.ktor.client.engine.android.Android
import kotlinx.coroutines.Dispatchers
import me.tbandawa.android.openweather.simplified.data.api.OpenWeatherApiClient
import me.tbandawa.android.openweather.simplified.data.repo.OpenWeatherRepoImpl
import me.tbandawa.android.openweather.simplified.data.viewmodel.OpenWeatherViewModel
import me.tbandawa.android.openweather.simplified.data.mapper.ResponseMapperImpl
import me.tbandawa.android.openweather.simplified.domain.repo.OpenWeatherRepo
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val dispatchersModule = module {
    single(named("IODispatcher")) { Dispatchers.IO }
    single(named("MainDispatcher")) { Dispatchers.Main }
}

private val clientEngineModule = module {
    single { Android }
}

private val apiModule = module {
    single { OpenWeatherApiClient(get()) }
}

private val mapperModule = module {
    single { ResponseMapperImpl() }
}

private val repoModule = module {
    single<OpenWeatherRepo> { OpenWeatherRepoImpl(get(), get(), get(named("IODispatcher"))) }
}

private val viewModelModule = module {
    single { OpenWeatherViewModel(get()) }
}

val modulesList = listOf(
    dispatchersModule,
    clientEngineModule,
    apiModule,
    mapperModule,
    repoModule,
    viewModelModule
)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(modulesList)
}