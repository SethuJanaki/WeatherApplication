package com.application.weather.data

import com.application.weather.R
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherData(
    var weather: ArrayList<Weather>? = null,
    var coord: Coord? = null,
    var base: String = "",
    var main: WeatherItem? = null,
    var visibility: String? = null,
    var wind: Wind? = null,
    var rain: Rain? = null,
    var clouds: Clouds? = null,
    var dt: String? = null,
    var sys: WeatherDataItem? = null,
    var timezone: String? = null,
    var id: String? = null,
    var name: String? = null,
    var cod: String? = null,
    var favorite: Boolean = false,
) : Serializable {

    class Coord(
        var lat: String? = "",
        var lon: String? = "",
    ) : Serializable

    class Weather(
        var id: Int? = null,
        var main: String? = "",
        var description: String? = "",
        var icon: String? = ""
    ) : Serializable

    class Wind(
        var speed: String? = null,
    ) : Serializable

    class Rain(
        @SerializedName("1h")
        var data: String? = "",
    ) : Serializable

    class Clouds(
        var all: String? = "",
    ) : Serializable

    class WeatherItem(
        var temp: String? = "",
        var temp_min: String? = "",
        var temp_max: String? = "",
        var humidity: String? = ""
    ) : Serializable

    class WeatherDataItem(
        var type: String? = "",
        var id: String? = "",
        var country: String? = "",
    ) : Serializable

}

