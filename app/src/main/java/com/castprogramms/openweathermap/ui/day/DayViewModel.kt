package com.castprogramms.openweathermap.ui.day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.weather.ForecastWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DayViewModel : ViewModel() {
    val unit: MutableLiveData<ForecastWeatherData> = MutableLiveData<ForecastWeatherData>()

    fun load(i : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val listWeather = WeatherApplication.database.weatherDao().getLastForecastWeatherData().reversed()
            unit.postValue(listWeather[i])

        }
    }
}