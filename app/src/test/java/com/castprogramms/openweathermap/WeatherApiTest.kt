package com.castprogramms.openweathermap

import com.castprogramms.openweathermap.database.data.weather.forecast.ForecastWeather
import com.castprogramms.openweathermap.network.*
import com.castprogramms.openweathermap.network.Reference
import com.castprogramms.openweathermap.ui.map.MapViewModel
import org.junit.Test

class WeatherApiTest {

    var view = MapViewModel()


    val QUERY_PARAM = QueryParam(
        LangFormat.EN,
        TempFormat.C,
        LocateFormat.City("Moscow")
    )

    @Test
    fun getOneDayWeather() {
        var response = Reference.WHEATHER.weatherUnit(QUERY_PARAM).execute().body()
        println(response.toString())
        view.
    }

    @Test
    fun getSevenDayWeather() {
        val QUANTITY = 7
        var response : ForecastWeather? =
            Reference.WHEATHER.forecastWeather("Moscow", QUANTITY).execute().body()
        response?.list?.forEach {
            println(it)
        }
    }
}