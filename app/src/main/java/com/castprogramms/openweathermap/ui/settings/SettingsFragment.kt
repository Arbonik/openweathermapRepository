package com.castprogramms.openweathermap.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.PreferenceFragmentCompat
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.WeatherApplication.Companion.setCity
import com.castprogramms.openweathermap.WeatherApplication.Companion.setGeolocationSettings
import com.castprogramms.openweathermap.WeatherApplication.Companion.setMetricSettings
import com.castprogramms.openweathermap.WeatherApplication.Companion.setRequestLanguage
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            resources.getString(R.string.lang_key) -> {
                setRequestLanguage(sharedPreferences, key)
            }
            resources.getString(R.string.geo_key) -> {
                setGeolocationSettings(sharedPreferences, key)
            }
            resources.getString(R.string.city_key) -> {
                setCity(sharedPreferences, key)
            }

            resources.getString(R.string.metric_key)->{
                setMetricSettings(sharedPreferences, key)
            }
            resources.getString(R.string.logout_key)->{
                FirebaseAuth.getInstance().signOut()
                sharedPreferences.edit(commit = true){
                    putBoolean(key, false)
                }
                requireActivity().finish()
            }
            else ->{}

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}