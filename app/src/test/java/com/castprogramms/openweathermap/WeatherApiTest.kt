package com.castprogramms.openweathermap

import com.castprogramms.openweathermap.network.*
import com.castprogramms.openweathermap.network.Reference
import org.junit.Test

class WeatherApiTest {

    val QUERY_PARAM_COORDINATES = QueryParam(
        LangFormat.EN,
        TempFormat.C,
        LocateFormat.Geolocation(35.0, 139.0)
    )
    val QUERY_PARAM_CITY = QueryParam(
        LangFormat.EN,
        TempFormat.C,
        LocateFormat.City("Moscow")
    )

    @Test
    fun getForecastWeatherByCoordinates() {
        var response = Reference.WHEATHER.forecastWeather(
            "35.0",
            "35",
            QUERY_PARAM_COORDINATES.langFormat.format,
            QUERY_PARAM_COORDINATES.tempFormat.format,
            40,
            API_KEY
        )
        println(response.request().url().toString())
        println(response.execute().body())
        println(response)
        println(response)
    }
    @Test
    fun getForecastWeatherByCity() {
        var response = Reference.WHEATHER.forecastWeather(
            (QUERY_PARAM_CITY.locateFormat as LocateFormat.City).city,
            QUERY_PARAM_CITY.langFormat.format,
            QUERY_PARAM_CITY.tempFormat.format,
            40,
            API_KEY
        )
        println(response.request().url().toString())
        println(response.execute().body())
        println(response)
        println(response)
    }
    @Test
    fun getOneDayWeatherByCity() {
        var response = Reference.WHEATHER.weatherUnit(
            (QUERY_PARAM_CITY.locateFormat as LocateFormat.City).city,
            QUERY_PARAM_CITY.langFormat.format,
            QUERY_PARAM_CITY.tempFormat.format,
            API_KEY
        )
        println(response.request().url().toString())
        println(response.execute().body())
        println(response)
        println(response)
    }

}