
package com.castprogramms.openweathermap

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.preference.PreferenceManager
import java.util.*


object LocalUtils {
    private var mLocale: Locale? = null

    fun setLocale(locale: Locale?) {
        mLocale = locale
        if (mLocale != null) {
            Locale.setDefault(mLocale)
        }
    }

    fun updateConfiguration(wrapper: ContextThemeWrapper) {
        if (mLocale != null) {
            val configuration = Configuration()
            configuration.setLocale(mLocale)
            wrapper.applyOverrideConfiguration(configuration)
        }
    }

    fun updateConfiguration(application: Application, configuration: Configuration?) {
        if (mLocale != null ) {
            val config = Configuration(configuration)
            config.setLocale(mLocale)
            val res = application.baseContext.resources
            res.updateConfiguration(configuration, res.getDisplayMetrics())
        }
    }

    fun getPrefLangCode(context: Context): String {
        val language = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("lang", "default").toString()
        if (language.equals("default"))
             return context.resources.configuration.locale.language
        else
            return language
    }


}