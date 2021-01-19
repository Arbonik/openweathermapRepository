package com.castprogramms.openweathermap.database.data.weather.forecast

import androidx.room.Embedded
import com.castprogramms.openweathermap.database.data.weather.oneDay.Coord

data class City(
    @Embedded(prefix = "coord_")
    val coord: Coord?= Coord(),
    val country: String? = "",
    val id: Int? = 0,
    val name: String? = "",
    val population: Double? = 0.0,
    val sunrise: Double? = 0.0,
    val sunset: Double? = 0.0,
    val timezone: Double? = 0.0
)