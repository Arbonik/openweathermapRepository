package com.castprogramms.openweathermap.ui.settings

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.preference.PreferenceFragmentCompat
import com.castprogramms.openweathermap.MainActivity
import com.castprogramms.openweathermap.R
import com.castprogramms.openweathermap.database.DataRepository
import com.castprogramms.openweathermap.network.LangFormat
import com.castprogramms.openweathermap.network.LocateFormat
import com.castprogramms.openweathermap.network.TempFormat
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    fun updateSettings(sharedPreferences: SharedPreferences, resources: Resources) {
        langFormatFromSettings(sharedPreferences, resources)
        tempFormatFromSettings(sharedPreferences, resources)
        locateFormatFromSettings(sharedPreferences, requireContext())
    }

    companion object{
        fun locateFormatFromSettings(
            sharedPreferences: SharedPreferences,
            context: Context
        ) {
            when (sharedPreferences.getBoolean(context.resources.getString(R.string.geo_key), true)) {
                true ->
                    DataRepository.QUERY_PARAM.locateFormat = LocateFormat.City(
                        sharedPreferences.getString(
                            context.resources.getString(R.string.city_key),
                            "Moscow"
                        ).toString()
                    )
                false -> {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.noGeolocation),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnCompleteListener {
                        if (it.result != null && it.isSuccessful) {
                            val location = it.result

                            Toast.makeText(
                                context,
                                "${location.latitude} ${location.longitude}",
                                Toast.LENGTH_LONG
                            ).show()
                            DataRepository.QUERY_PARAM.locateFormat = LocateFormat.Geolocation(
                                location.latitude,
                                location.longitude
                            )
                        }
                    }
                }
            }
        }

        fun tempFormatFromSettings(
            sharedPreferences: SharedPreferences,
            resources: Resources
        ) {
            DataRepository.QUERY_PARAM.tempFormat =
                when (sharedPreferences.getString(resources.getString(R.string.metric_key), "c")) {
                    "c" -> TempFormat.C
                    "f" -> TempFormat.F
                    "k" -> TempFormat.K
                    else -> TempFormat.C
                }
        }

        fun langFormatFromSettings(
            sharedPreferences: SharedPreferences,
            resources: Resources
        ) {
            DataRepository.QUERY_PARAM.langFormat =
                when (sharedPreferences.getString(resources.getString(R.string.lang_key), "en")) {
                    "en" -> LangFormat.EN
                    "ru" -> LangFormat.RU
                    else -> LangFormat.EN
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

    }
    override fun onResume() {
        super.onResume()
        val locate = DataRepository.QUERY_PARAM.locateFormat
        when (locate){
            is LocateFormat.City ->  (requireActivity() as MainActivity).setNewTitle(R.string.title_settings)
            is LocateFormat.Geolocation -> (requireActivity() as MainActivity).setNewTitle(R.string.title_settings)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        updateSettings(sharedPreferences, resources)

        if (key == resources.getString(R.string.logout_key)) {
            FirebaseAuth.getInstance().signOut()
            sharedPreferences.edit(commit = true) {
                putBoolean(key, false)
            }
            requireActivity().finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}