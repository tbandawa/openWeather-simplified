package me.tbandawa.android.openweather.simplified.domain.mapper

import me.tbandawa.android.openweather.simplified.data.responses.RootResponse
import me.tbandawa.android.openweather.simplified.domain.model.Root
import me.tbandawa.android.openweather.simplified.domain.model.Main
import me.tbandawa.android.openweather.simplified.domain.model.List
import me.tbandawa.android.openweather.simplified.domain.model.City
import me.tbandawa.android.openweather.simplified.domain.model.Clouds
import me.tbandawa.android.openweather.simplified.domain.model.Coord
import me.tbandawa.android.openweather.simplified.domain.model.Weather
import me.tbandawa.android.openweather.simplified.domain.model.Wind
import me.tbandawa.android.openweather.simplified.domain.model.Sys

class FiveDayWeatherResponseMapper: ResponseMapper<RootResponse, Root> {

    override fun mapToModel(response: RootResponse): Root {
        return Root(
            response.cod,
            response.message,
            response.cnt,
            response.list.map { list ->
                List(
                    list.dt,
                    Main(
                        list.main.temp,
                        list.main.feelsLike,
                        list.main.tempMin,
                        list.main.tempMax,
                        list.main.pressure,
                        list.main.seaLevel,
                        list.main.grndLevel,
                        list.main.humidity,
                        list.main.tempKf
                    ),
                    list.weather.map { weather ->
                        Weather(
                            weather.id,
                            weather.main,
                            weather.description,
                            weather.icon
                        )
                    },
                    Clouds(
                        list.clouds.all
                    ),
                    Wind(
                        list.wind.speed,
                        list.wind.deg,
                        list.wind.gust
                    ),
                    list.visibility,
                    list.pop,
                    Sys(
                        list.sys.pod
                    ),
                    list.dtTxt
                )
            },
            City(
                response.city.id,
                response.city.name,
                Coord(
                    response.city.coord.lon,
                    response.city.coord.lat
                ),
                response.city.country,
                response.city.population,
                response.city.timezone,
                response.city.sunrise,
                response.city.sunset
            )
        )
    }
}
