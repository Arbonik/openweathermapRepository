package com.castprogramms.openweathermap.database.data.weather

interface DayParameter {
    val dataDay: String
    val nameDay: String
    val conditionIconUrl : String
    val temperature :  Double
}