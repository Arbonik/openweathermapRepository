package com.castprogramms.openweathermap

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.castprogramms.openweathermap.database.AppDatabase
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.database.GeoRepository
import com.castprogramms.openweathermap.network.LangFormat
import com.castprogramms.openweathermap.network.LocateFormat
import com.castprogramms.openweathermap.network.QueryParam
import com.castprogramms.openweathermap.network.TempFormat
import com.castprogramms.openweathermap.ui.settings.SettingsFragment
import java.util.*

class WeatherApplication : Application() {
    private val osLang = "default"
    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            this
        )
    }
    private lateinit var locale: Locale
    private lateinit var lang: String

    override fun onCreate() {
        appContext = applicationContext
        application = this
        lang = sharedPreferences.getString("lang", osLang).toString()
        if (lang == osLang)
            lang = resources.configuration.locale.country


        loadSettings()


        languageConfiguration()
        Locale.setDefault(locale)

        super.onCreate()

        LocalUtils.setLocale(Locale(LocalUtils.getPrefLangCode(this)))
        LocalUtils.updateConfiguration(this, resources.configuration)
    }


    private fun loadSettings() {
//        SettingsFragment.updateSettings(sharedPreferences, resources)
    }

    private fun languageConfiguration() {
        locale = Locale(lang)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, null)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val config = Configuration()
        locale = Locale(lang)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, null)
    }



    companion object {
        private lateinit var appContext: Context
        lateinit var application: WeatherApplication
        val database: AppDatabase by lazy {
            val LOCK = Any()
            synchronized(LOCK) {
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "database"
                )
                    .build()
            }
        }
        val repository: DataRepository by lazy {
            val LOCK = Any()
            synchronized(LOCK) {
                DataRepository()
            }
        }

        val geoRepository: GeoRepository by lazy {
            GeoRepository(appContext)
        }
    }
}