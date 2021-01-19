package com.castprogramms.openweathermap

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.castprogramms.openweathermap.database.data.weather.oneDay.Weather
import com.castprogramms.openweathermap.database.data.weather.oneDay.WeathermanResponse
import com.castprogramms.openweathermap.database.AppDatabase
import com.castprogramms.openweathermap.database.data.map.LocationDao
import com.castprogramms.openweathermap.database.data.map.MyLocation
import com.castprogramms.openweathermap.database.data.weather.WeatherDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var weatherDao: WeatherDao
    private lateinit var locationDao: LocationDao
    private lateinit var db: AppDatabase
    private val corotineScope = CoroutineScope(Job() + Dispatchers.Main)
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        weatherDao = db.weatherDao()
        locationDao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() {
//        val user: WeathermanResponse = WeathermanResponse(id = 1).apply {
//            weather = listOf(Weather(id = 123).apply {
//                weatherid = 1
//            })
//        }
//        weatherDao.insert(user)
//        weatherDao.insert(user.weather)
//
//        val weathermanResponse = weatherDao.getWeathersResponse()
//        val weather = weatherDao.getWeathers(1)
//        weathermanResponse[0].weather = weather
//        assertThat(user, equalTo(weathermanResponse[0]))
//    }

    @Test
    @Throws(java.lang.Exception::class)
    fun writeUserAndReadLocation(){
        val myLocation = MyLocation(53.36, 83.76)
        corotineScope.launch {
            locationDao.addLocation(myLocation)
            val locations = locationDao.getAllLocation()
            assertThat(myLocation, equalTo(locations[0]))
        }
    }
}