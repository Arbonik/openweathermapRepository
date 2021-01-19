package com.castprogramms.openweathermap

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.castprogramms.openweathermap.database.AppDatabase
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.network.LangFormat
import com.castprogramms.openweathermap.network.LocateFormat
import com.castprogramms.openweathermap.network.TempFormat
import com.google.android.gms.location.LocationServices
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
        setMetricSettings(sharedPreferences, resources.getString(R.string.metric_key))
        setGeolocationSettings(sharedPreferences, resources.getString(R.string.geo_key))
        setRequestLanguage(sharedPreferences, resources.getString(R.string.lang_key))
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

        val database: AppDatabase by lazy {
            val LOCK = Any()
            synchronized(LOCK) {
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "database"
                ).fallbackToDestructiveMigration()
                    .build()
            }
        }
        val repository: DataRepository by lazy {
            val LOCK = Any()
            synchronized(LOCK) {
                DataRepository(database.locationDao())
            }
        }

        fun setMetricSettings(
            sharedPreferences: SharedPreferences,
            key: String
        ) {
            DataRepository.QUERY_PARAM.tempFormat = when (sharedPreferences.getString(key, "")) {
                "c" -> TempFormat.C
                "f" -> TempFormat.F
                "k" -> TempFormat.K
                else -> TempFormat.F
            }
        }

        fun setGeolocationSettings(
            sharedPreferences: SharedPreferences,
            key: String
        ) {
            val isGeolocation = sharedPreferences.getBoolean(key, false)
            if (isGeolocation) {

                LocationServices.getFusedLocationProviderClient(appContext).lastLocation.addOnCompleteListener {
                    if (it.isSuccessful && it.result!= null)
                    DataRepository.QUERY_PARAM.locate = LocateFormat.Geolocation(it.result.longitude, it.result.latitude)
                }
            } else {
                setCity(sharedPreferences, "city_key")
            }
        }

        fun setRequestLanguage(
            sharedPreferences: SharedPreferences,
            key: String
        ) {
            DataRepository.QUERY_PARAM.langFormat =
                when (sharedPreferences.getString(key, "")) {
                    "en" -> LangFormat.EN
                    "ru" -> LangFormat.RU
                    else -> LangFormat.EN
                }
        }

        fun setCity(sharedPreferences: SharedPreferences, cityKey: String) {
            val city = sharedPreferences.getString(cityKey, "Moscow") ?: "Moscow"
            DataRepository.QUERY_PARAM.locate = LocateFormat.City(city)
        }
    }
}