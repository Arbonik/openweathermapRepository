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

    val gpsTracker : GeoTracker = GeoRepository(getApplication())
    val mutableLiveDataThisPosition = gpsTracker.currentGeoPosition

    var mutableLiveLocations : MutableLiveData<MutableList<MyLocation>>

    init {
        mutableLiveLocations = gpsTracker.getAllGEOPosition()
    }
    var isTracking = false
    val mutableLiveDataTracking = MutableLiveData<Boolean>().apply {
        value = isTracking
    }
    lateinit var dataLiveData : LiveData<List<MyLocation>>

    var firstLaunch = true

    fun updateLocationData(){
        val repository = WeatherApplication.database
        viewModelScope.launch{
            dataLiveData = repository.locationDao().getAllLocation()

//            Log.e("Test", locations.size.toString())
        }
    }

    fun addLocation(location: MyLocation){
//        locations.add(MyLocation(location.latitude, location.longitude))
//        mutableLiveLocations.value = locations
        val database = WeatherApplication.database
        viewModelScope.launch {
            database.locationDao().addLocation(location)
        }
    }

    fun startTracking(context: Context){
//        listener = MyLocationListener.setUpLocationListener(context, this)
    }

    fun covertToLatLngList(mutableList: List<MyLocation>):MutableList<LatLng>{
        val mutableListLatLng = mutableListOf<LatLng>()
        mutableList.forEach {
            mutableListLatLng.add(
                LatLng(it.latitude, it.longitude)
            )
        }
        return mutableListLatLng
    }

    fun setIsTracking(isTracking: Boolean, context: Context){
        isNotFirstLaunchFrag = true
        this.isTracking = isTracking
        mutableLiveDataTracking.value = this.isTracking
        if (isTracking){
//            clear()
            startTracking(context)
        }
        else{
            stopTracking()
        }
    }

    fun stopTracking(){
//        listener.stopTracking(listener)
        firstLaunch = true
    }

    fun getIsFirstLaunchFrag() = isNotFirstLaunchFrag
    companion object{
        var isNotFirstLaunchFrag = false
    }
}