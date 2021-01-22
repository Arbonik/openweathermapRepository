package com.castprogramms.openweathermap.ui.map

import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.GeoRepository
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    val gpsTracker  = WeatherApplication.geoRepository
    val mutableLiveDataThisPosition = gpsTracker.currentGeoPosition
    val mutableLiveDataTracking : MutableLiveData<Boolean> = gpsTracker.isListenerActive
    var dataLiveData : LiveData<List<MyLocation>> = WeatherApplication.database.locationDao().getAllLocation()

    fun covertToLatLngList(mutableList: List<MyLocation>):MutableList<LatLng>{
        val mutableListLatLng = mutableListOf<LatLng>()
        mutableList.forEach {
            mutableListLatLng.add(
                LatLng(it.latitude, it.longitude)
            )
        }
        return mutableListLatLng
    }
    fun startTracking(){
        gpsTracker.startListener()
        viewModelScope.launch {
            WeatherApplication.database.locationDao().deleteAllLocation()
        }
    }

    fun stopTracking(){
        gpsTracker.stopListener()
    }

}