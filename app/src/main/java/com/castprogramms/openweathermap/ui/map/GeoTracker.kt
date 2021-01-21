package com.castprogramms.openweathermap.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import com.castprogramms.openweathermap.database.data.map.MyLocation

interface GeoTracker {

    val currentGeoPosition : LiveData<Location>

    suspend fun getAllGEOPosition() : LiveData<List<MyLocation>>

}
