package com.castprogramms.openweathermap.ui.week

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeekViewModel : ViewModel() {
    private var _days = WeatherApplication.database.weatherDao().getForecastWeatherData()
    val days: LiveData<List<ForecastWeatherData?>> = _days

    fun downloadData(){
        viewModelScope.launch(Dispatchers.IO) {
            WeatherApplication.repository.getCurrentMoreWeather()
        }
    }
}
