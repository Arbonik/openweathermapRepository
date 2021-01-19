package com.castprogramms.openweathermap.database.data.weather.forecast

data class WeatherInfo(
    val clouds: Clouds?,
    val dt: Double? = 0.0,
    val dt_txt: String?,
    val main: Main?,
    val pop: Double? = 0.0,
    val sys: Sys?,
    val visibility: Double?,
    val weather: List<Weather>?,
    val wind: Wind?
)