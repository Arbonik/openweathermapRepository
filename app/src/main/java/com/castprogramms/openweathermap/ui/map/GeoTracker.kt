package com.castprogramms.openweathermap.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.castprogramms.openweathermap.database.data.map.MyLocation

interface GeoTracker {

    val currentGeoPosition : LiveData<Location>

    fun getAllGEOPosition() : MutableLiveData<MutableList<MyLocation>>

}
