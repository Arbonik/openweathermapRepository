package com.castprogramms.openweathermap.database.data.weather.oneDay

data class WeathermanResponse(
    var base: String = "",
    var clouds: Clouds = Clouds(),
    var cod: Int = 0,
    var coord: Coord = Coord(),
    var dt: Int = 0,
    var id: Int = 0,
    var main: Main = Main(),
    var name: String = "",
    var sys: Sys = Sys(),
    var timezone: Int = 0,
    var visibility: Double = 0.0,
    var weather: List<Weather>? = null,
    var wind: Wind = Wind()
)