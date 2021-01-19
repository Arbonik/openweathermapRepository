package com.castprogramms.openweathermap.database.data.weather.forecast

data class Main(
    val feels_like: Double?,
    val grnd_level: Double? = 0.0,
    val humidity: Double? = 0.0,
    val pressure: Double? = 0.0,
    val sea_level: Double? = 0.0,
    val temp: Double?,
    val temp_kf: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)