package com.castprogramms.openweathermap.database.data.map

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MyLocation (
    var latitude: Double,
    var longitude: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    override fun equals(other: Any?): Boolean {
        if (other is MyLocation){
            return other.latitude == this.latitude && other.longitude == this.longitude
        }
        else
            return false
    }

    constructor(location : Location) :this(location.latitude, location.longitude)
}