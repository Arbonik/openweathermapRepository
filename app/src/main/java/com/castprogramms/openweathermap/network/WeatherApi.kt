package com.castprogramms.openweathermap.network

import com.castprogramms.openweathermap.database.data.weather.forecast.ForecastWeather
import com.castprogramms.openweathermap.database.data.weather.oneDay.WeathermanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "ec930b8b959d1e969a3dc455d2f87b3f"

//http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=ec930b8b959d1e969a3dc455d2f87b3f

interface WeatherApi {
    @GET("weather/")
    fun weatherUnit(
        @Query("q")  city : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("appid") api : String = API_KEY) : Call<WeathermanResponse>

    @GET("weather/")
    fun weatherUnit(
        @Query("lan")  lan : String,
        @Query("lon")  lon : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("appid") api : String = API_KEY) : Call<WeathermanResponse>

    @GET("forecast/")
    fun forecastWeather(
        @Query("q") city: String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("cnt") quantity : Int = 30,
        @Query("appid") api : String = API_KEY): Call<ForecastWeather>

    @GET("forecast/")
    fun forecastWeather(
        @Query("lan")  lan : String,
        @Query("lon")  lon : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("cnt") quantity : Int = 30,
        @Query("appid") api : String = API_KEY): Call<ForecastWeather>
}