package com.castprogramms.openweathermap.ui.now

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.weather.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NowViewModel : ViewModel() {


    private var _currentUnit = WeatherApplication.database.weatherDao().getWeathersUnit()

    val currentUnit: LiveData<WeatherData?> = _currentUnit

    fun downloadData(){
        viewModelScope.launch(Dispatchers.IO) {
            WeatherApplication.repository.getCurrentUnitWeather()
        }
    }
}