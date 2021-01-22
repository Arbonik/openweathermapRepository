package com.castprogramms.openweathermap.network

data class QueryParam(
    var langFormat : LangFormat,
    var tempFormat : TempFormat,
    var locateFormat: LocateFormat
    )

enum class LangFormat(val format: String) {
    RU("ru"),
    EN("en")
}

enum class TempFormat(val format: String) {
    K("standard"),
    F("imperial"),
    C("metric")
}

sealed class LocateFormat{
    class City(val city : String) : LocateFormat()
    class Geolocation(val latitude : Double, val longitude : Double) : LocateFormat()

}
