# <b>openWeather-simplified</b>
A simple weather app using MVI architecture whilst following best practices. This approach allows for independent development of different layers of the app with much testability. Data is provided by [OpenWeather](https://openweathermap.org/forecast5)

#### App Architecture

<p align="center">
	<img src="/blob/application_architecture.png" width=100% height=40% alt="open Weather Screenshot">
</p>

This project is built using a simple MVI<b>(Model-View-Intent)</b> architecture with unidirectional and cylindrical flow of data.<p>
User performs an action which is an <b>Intent</b> -> the intent request for data change to <b>Model</b> -> model changes state and <b>View</b> updates.

#### Libraries and Tools
* [Kotlin](https://kotlinlang.org/)
* [Coil](https://coil-kt.github.io/coil/) image loading library for Android Jetpack Compose support
* [Koin](https://insert-koin.io/) for dependency injection.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) libraries.
* [Ktor](https://ktor.io/) for fetching data from the OpenWeatherMap API.
