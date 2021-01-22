package com.castprogramms.openweathermap.database.data.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.castprogramms.openweathermap.database.data.weather.oneDay.WeathermanResponse

@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(weatherResponse: ForecastWeatherData)

    @Insert
    suspend fun insert(weatherResponse: List<ForecastWeatherData>)

    @Insert
    suspend fun insert(unitSpecificCurrentWeatherEntry: WeatherData)

    @Query("SELECT * FROM weatherData ORDER BY id DESC LIMIT 1")
    fun getWeathersUnit():WeatherData?

    @Query("SELECT * FROM forecastWeatherData ORDER BY id DESC LIMIT 30")
    fun getForecastWeatherData():LiveData<List<ForecastWeatherData?>>

    @Query("SELECT * FROM forecastWeatherData ORDER BY id DESC LIMIT 30")
    fun getLastForecastWeatherData():List<ForecastWeatherData?>

}