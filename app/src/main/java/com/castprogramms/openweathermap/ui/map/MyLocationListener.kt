package com.castprogramms.openweathermap.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.function.Consumer

class MyLocationListener: LocationListener {
    var imHere: Location? = null
    lateinit var locationManager: LocationManager
    override fun onLocationChanged(location: Location) {
        imHere = location
    }
    companion object {
        fun setUpLocationListener(context: Context): MyLocationListener {
            val locationListener = MyLocationListener()

            locationListener.locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//            return
            }
            locationListener.locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, 10f, locationListener
            )
            return locationListener
        }
    }
    fun stopTracking(locationListener: MyLocationListener) {
        try {
            locationManager.removeUpdates(locationListener)
        } catch (e: Exception) {
            Log.e("Test", e.message.toString())
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {
    }
}