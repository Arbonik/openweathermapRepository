package com.castprogramms.openweathermap.database

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.WeatherApplication
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.castprogramms.openweathermap.ui.map.GeoTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GeoRepository(val context: Context) : GeoTracker, LocationListener {

    val isListenerActive: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationManager: LocationManager
    private val geoCurrentPositionLive = MutableLiveData<Location>()
    override val currentGeoPosition: LiveData<Location> = geoCurrentPositionLive


    init {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)
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
        }
        fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    geoCurrentPositionLive.value = it.result
                }
                else
                    Log.e("Test", it.exception?.localizedMessage.toString())
            }

    }
    fun startListener(){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000, 10f, this
        )
        isListenerActive.postValue(true)
    }

    fun stopListener() {
        locationManager.removeUpdates(this)
        isListenerActive.postValue(false)
    }

    override fun onLocationChanged(location: Location) {
        GlobalScope.launch(Dispatchers.IO) {
            WeatherApplication.database.locationDao().addLocation(MyLocation(location))
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
//        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText(context, context.getString(R.string.power_geolocation_alert), Toast.LENGTH_LONG).show()
//        super.onProviderDisabled(provider)
    }
}