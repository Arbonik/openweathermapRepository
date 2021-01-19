package com.castprogramms.openweathermap.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.castprogramms.openweathermap.database.data.map.LocationDao
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import com.castprogramms.openweathermap.database.data.weather.WeatherDao
import com.castprogramms.openweathermap.database.data.weather.WeatherData

@Database(
    entities = arrayOf(
        MyLocation::class,
        WeatherData::class,
        ForecastWeatherData::class
    ), version = 1, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
    abstract fun locationDao(): LocationDao
}