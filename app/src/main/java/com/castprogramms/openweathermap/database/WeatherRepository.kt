package com.castprogramms.openweathermap.database

import androidx.lifecycle.LiveData
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData

interface WeatherRepository {
    suspend fun getCurrentUnitWeather()

    suspend fun getCurrentMoreWeather() : LiveData<List<ForecastWeatherData?>>
}