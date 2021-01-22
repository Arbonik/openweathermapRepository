package com.castprogramms.openweathermap.database.data.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.castprogramms.openweathermap.database.data.weather.forecast.ForecastWeather
import com.castprogramms.openweathermap.tools.WeatherConverter

@Entity(tableName = "forecastWeatherData")
data class ForecastWeatherData(
    override val dataDay: String = "",
    override val conditionIconUrl: String = "",
    override val temperature: Double = 0.0,
    override val nameDay: String = "",
    val windSpeed: Double = 0.0,
    val windDirection: String ="",
    val feelsLikeTemperature: Double = 0.0,
    val visibilityDistance: Double = 0.0,
    val conditionText: String = ""
) : DayParameter {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

class BossForecastWeatherData(val forecastWeathersData: ForecastWeatherData) : UnitSpecificCurrentWeatherEntry{
    override val temperature: Double = forecastWeathersData.temperature
    override val conditionText: String = forecastWeathersData.conditionText
    override val conditionIconUrl: String = forecastWeathersData.conditionIconUrl
    override val windSpeed: Double = forecastWeathersData.windSpeed
    override val windDirection: Int = WeatherConverter.convertToWay(forecastWeathersData.windDirection.toDouble())
    override val feelsLikeTemperature: Double = forecastWeathersData.feelsLikeTemperature
    override val visibilityDistance: Double = forecastWeathersData.visibilityDistance

}

data class ForecastWeatherAdapter(
    var forecastWeather: ForecastWeather = ForecastWeather(),
    val forecastWeathersData: List<ForecastWeatherData> =
        List(forecastWeather.list.size ) {
            ForecastWeatherData(
                forecastWeather.list[it].dt_txt!!,
                forecastWeather.list[it].weather?.get(0)?.icon!!,
                forecastWeather.list[it].main?.temp!!,
                forecastWeather.list[it].main?.humidity.toString(),
                forecastWeather.list[it].wind?.speed!!,
                forecastWeather.list[it].wind?.deg!!,
                forecastWeather.list[it].main?.feels_like!!,
                forecastWeather.list[it].visibility!!,
                forecastWeather.list[it].weather!!.get(0).description!!
            )
        }
)