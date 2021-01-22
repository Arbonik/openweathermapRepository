package com.castprogramms.openweathermap.database

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.castprogramms.openweathermap.ui.map.GeoTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class GeoRepository(val context: Context) : GeoTracker, LocationListener {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationManager: LocationManager
    val locations = mutableListOf<MyLocation>()
    val mutableLiveDataLocations = MutableLiveData<MutableList<MyLocation>>(locations)
    private val geoCurrentPositionLive = MutableLiveData<Location>()
    override val currentGeoPosition: LiveData<Location> = geoCurrentPositionLive

    var isTracking = false

    override fun getAllGEOPosition(): MutableLiveData<MutableList<MyLocation>> {
        return mutableLiveDataLocations
    }

    init {
        locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    geoCurrentPositionLive.value = it.result
                    Log.d("Coocrd", "NewValue")
                }

            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, 10f, this
            )
        }


    }

    fun stopListener() {
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        locations.add(MyLocation(location))
        mutableLiveDataLocations.value = locations
    }
}