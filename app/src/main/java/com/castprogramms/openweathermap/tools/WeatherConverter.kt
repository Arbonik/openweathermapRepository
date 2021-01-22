package com.castprogramms.openweathermap.tools

import android.content.Context
import com.castprogramms.openweathermap.R

class WeatherConverter {
    companion object{
        fun convertToWay(deg: Double):Int{
            return when (deg) {
                in 337.5..360.0 -> return R.string.sever//"C"
                in 0.0..22.5 -> return R.string.sever//"C"
                in 22.5..67.5 -> return R.string.sever_vostok//"C-B"
                in 67.5..112.5 -> return R.string.vostok//"B"
                in 112.5..157.5 -> return R.string.ygo_vostok//"Ю-В"
                in 157.5..202.5 -> return R.string.yg//"Ю"
                in 202.5..247.5 -> return R.string.ygo_zapad//"Ю-З"
                in 247.5..292.5 -> return R.string.zapad//"З"
                in 292.5..337.5 -> return R.string.sever_zapad//"C-З"
                else -> R.string.welcome
            }
        }
    }
}