package com.castprogramms.openweathermap.database

import androidx.lifecycle.LiveData
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import com.castprogramms.openweathermap.database.data.weather.WeatherData

interface WeatherRepository {
    suspend fun getCurrentUnitWeather() : WeatherData?

    suspend fun getCurrentMoreWeather() : LiveData<List<ForecastWeatherData?>>
}