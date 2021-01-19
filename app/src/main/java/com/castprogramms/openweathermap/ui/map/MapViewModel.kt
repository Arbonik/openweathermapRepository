package com.castprogramms.openweathermap.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MapViewModel : ViewModel() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locations = mutableListOf<MyLocation>()
    val mutableLiveLocations = MutableLiveData<MutableList<MyLocation>>().apply {
        value = locations
    }
    var isTracking = false
    val mutableLiveDataTracking = MutableLiveData<Boolean>().apply {
        value = isTracking
    }
    val mutableLiveDataThisPosition = MutableLiveData<Location>()
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
        isTracking = true
        listener = MyLocationListener.setUpLocationListener(context, this)
        if (firstLaunch) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful) {
                    firstLocation = it.result
                    mutableLiveDataThisPosition.value = firstLocation
                }
            }
            firstLaunch = false
        }
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

    fun clear(){
        locations = mutableListOf()
        mutableLiveLocations.value = locations
    }
    fun getIsFirstLaunchFrag() = isNotFirstLaunchFrag
    companion object{
        var isNotFirstLaunchFrag = false
    }
}