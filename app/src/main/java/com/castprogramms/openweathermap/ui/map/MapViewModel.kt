package com.castprogramms.openweathermap.ui.map

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

class MapViewModel : AndroidViewModel(WeatherApplication.application) {

    val gpsTracker : GeoTracker = GeoRepository(getApplication())
    val mutableLiveDataThisPosition = gpsTracker.currentGeoPosition

    lateinit var mutableLiveLocations : LiveData<List<MyLocation>>

    init {
        viewModelScope.launch {
            mutableLiveLocations = gpsTracker.getAllGEOPosition()
        }
    }

    val mutableLiveDataTracking = MutableLiveData<Boolean>().apply {
        value = false
    }

    var firstLaunch = true

    private lateinit var listener: MyLocationListener
    lateinit var firstLocation: Location


    fun updateLocationData(){
        val repository = WeatherApplication.repository
        viewModelScope.launch {
            locations = repository.getTrack().value?.toMutableList()!!
            mutableLiveLocations.value = locations
            Log.e("Test", locations.size.toString())
        }
    }

    fun addLocation(location: Location){
        locations.add(MyLocation(location.latitude, location.longitude))
        mutableLiveLocations.value = locations
        val database = WeatherApplication.database
        viewModelScope.launch {
            database.locationDao().addLocation(MyLocation(location.latitude, location.longitude))
        }
    }

    fun startTracking(context: Context){
        listener = MyLocationListener.setUpLocationListener(context, this)
    }

    fun covertToLatLngList(mutableList: MutableList<MyLocation>):MutableList<LatLng>{
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
            clear()
            startTracking(context)
        }
        else{
            stopTracking()
        }
    }

    fun stopTracking(){
        listener.stopTracking(listener)
        firstLaunch = true
    }

    fun getIsFirstLaunchFrag() = isNotFirstLaunchFrag
    companion object{
        var isNotFirstLaunchFrag = false
    }
}

//class MapViewModelFactory :