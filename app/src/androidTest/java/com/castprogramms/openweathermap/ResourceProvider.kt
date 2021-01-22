package com.castprogramms.openweathermap

import android.content.res.Resources
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes res: Int, vararg args : Any): String
}
class AppResourceProvider(private val resources: Resources) : ResourceProvider{
    override fun getString(res: Int, vararg args: Any): String {
        return resources.getString(res, *args)
    }
}

class TestResource : ResourceProvider {
    override fun getString(res: Int, vararg args: Any): String = "$res"
}