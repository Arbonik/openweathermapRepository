package com.castprogramms.openweathermap.database.data.weather

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.castprogramms.openweathermap.database.data.weather.oneDay.WeathermanResponse

@Entity(tableName = "weatherData")
data class WeatherData(
    @Ignore
    var weathermanResponse: WeathermanResponse = WeathermanResponse(),
    override var temperature: Double = weathermanResponse.main.temp,
    override var conditionText: String = weathermanResponse.weather?.get(0)?.description ?: "",
    override var conditionIconUrl: String = weathermanResponse.weather?.get(0)?.icon ?: "",
    override var windSpeed: Double = weathermanResponse.wind.speed,
    override var windDirection: String = weathermanResponse.wind.deg.toString(),
    override var feelsLikeTemperature: Double = weathermanResponse.main.feels_like,
    override var visibilityDistance: Double = weathermanResponse.visibility
) : UnitSpecificCurrentWeatherEntry {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor() : this(WeathermanResponse())
    constructor(
        forecastWeatherData: ForecastWeatherData
    ) : this(
        WeathermanResponse(),
        forecastWeatherData.temperature,
        forecastWeatherData.conditionText,
        forecastWeatherData.conditionIconUrl,
        forecastWeatherData.windSpeed,
        forecastWeatherData.windDirection.toString(),
        forecastWeatherData.feelsLikeTemperature,
        forecastWeatherData.visibilityDistance
    )
}