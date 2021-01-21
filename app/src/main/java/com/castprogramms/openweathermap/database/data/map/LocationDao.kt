package com.castprogramms.openweathermap.database.data.map

import androidx.lifecycle.LiveData
import androidx.room.*
import com.castprogramms.openweathermap.database.data.map.MyLocation

@Dao
interface LocationDao {
    @Query("SELECT * FROM MyLocation")
    suspend fun getAllLocation(): LiveData<List<MyLocation>>

    @Insert
    suspend fun addLocation(myLocation: MyLocation)

    @Query("DELETE FROM MyLocation")
    suspend fun deleteAllLocation()
}