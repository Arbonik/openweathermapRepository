package com.castprogramms.openweathermap.database.data.weather


interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}