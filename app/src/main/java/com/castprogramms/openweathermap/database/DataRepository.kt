package com.castprogramms.openweathermap.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherAdapter
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import com.castprogramms.openweathermap.database.data.weather.UnitSpecificCurrentWeatherEntry
import com.castprogramms.openweathermap.database.data.weather.WeatherData
import com.castprogramms.openweathermap.database.data.weather.forecast.ForecastWeather
import com.castprogramms.openweathermap.database.data.weather.oneDay.WeathermanResponse
import com.castprogramms.openweathermap.network.*
import retrofit2.Call
import java.io.IOException
import java.net.UnknownHostException

class DataRepository() : WeatherRepository {

    val database = WeatherApplication.database.weatherDao()

    override suspend fun getCurrentUnitWeather() : WeatherData?{
        try {
            var response: Call<WeathermanResponse>? = null

            if (QUERY_PARAM.locateFormat is LocateFormat.City) {
                response = Reference.WHEATHER.weatherUnit(
                    (QUERY_PARAM.locateFormat as LocateFormat.City).city,
                    QUERY_PARAM.langFormat.format,
                    QUERY_PARAM.tempFormat.format
                )
            }

            if (QUERY_PARAM.locateFormat is LocateFormat.Geolocation) {
                response = Reference.WHEATHER.weatherUnitByCoord(
                    (QUERY_PARAM.locateFormat as LocateFormat.Geolocation).latitude.toString(),
                    (QUERY_PARAM.locateFormat as LocateFormat.Geolocation).longitude.toString(),
                    QUERY_PARAM.langFormat.format,
                    QUERY_PARAM.tempFormat.format
                )
            }
            val weathermanResponse = response
            Log.d("IS RESPONSE", weathermanResponse?.request()?.url().toString())
            Log.d("IS RESPONSE", weathermanResponse.toString())
            val r = weathermanResponse?.execute()?.body()
            if (r != null)
                database.insert(WeatherData(r))
        } catch (e: IOException) {
        } catch (e: UnknownHostException) {
        }
        return database.getWeathersUnit()
    }

    override suspend fun getCurrentMoreWeather(): LiveData<List<ForecastWeatherData?>> {
        try {

            var response: Call<ForecastWeather>? = null
            if (QUERY_PARAM.locateFormat is LocateFormat.City) {
                response = Reference.WHEATHER.forecastWeather(
                    (QUERY_PARAM.locateFormat as LocateFormat.City).city,
                    QUERY_PARAM.langFormat.format,
                    QUERY_PARAM.tempFormat.format
                )
            }

            if (QUERY_PARAM.locateFormat is LocateFormat.Geolocation) {
                response = Reference.WHEATHER.forecastWeatherByCoord(
                    (QUERY_PARAM.locateFormat as LocateFormat.Geolocation).latitude.toString(),
                    (QUERY_PARAM.locateFormat as LocateFormat.Geolocation).longitude.toString(),
                    QUERY_PARAM.langFormat.format,
                    QUERY_PARAM.tempFormat.format
                )
            }
            val forecastWeather: ForecastWeather? = response?.execute()?.body()

            if (forecastWeather != null) {
                val adapter = ForecastWeatherAdapter(forecastWeather)
                database.insert(adapter.forecastWeathersData)
            }

        } catch (e: IOException) {
        } catch (e: UnknownHostException) {

        }
        return database.getForecastWeatherData()
    }

    companion object {
        var QUERY_PARAM: QueryParam =
            QueryParam(
                LangFormat.EN,
                TempFormat.C,
                LocateFormat.City("Moscow")
            )
    }
}