package com.castprogramms.openweathermap.network

object Reference {
    val WHEATHER : WeatherApi
        get() = RetrofitClient.retrofit.create(WeatherApi::class.java)
}