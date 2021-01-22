package com.castprogramms.openweathermap.database.data.map

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocationDao {
    @Query("SELECT * FROM myLocation")
    fun getAllLocation(): LiveData<List<MyLocation>>

    @Insert
    suspend fun addLocation(myLocation: MyLocation)

    @Query("DELETE FROM myLocation")
    suspend fun deleteAllLocation()
}