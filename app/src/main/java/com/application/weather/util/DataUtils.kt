package com.application.weather.util

import com.application.weather.R
import java.text.SimpleDateFormat
import java.util.*

object DataUtils {

    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timestamp * 1000L
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy  HH:mm aa", Locale.getDefault())
        return sdf.format(calendar.time).toString()
    }

    fun tempToCelsius(temp: Double): String {
        return (temp - 273.15).toInt().toString()
    }

    fun tempToFarenheit(temp: Double): String {
        return ((temp - 273.15) * 9 / 5 + 32).toInt().toString()
    }

    fun getTemperature(min: Double, max: Double): String {
        return tempToCelsius(min) + " - " + tempToCelsius(max)
    }

    fun getImage(data: Int?): Int {
        return when (data) {
            in 201..300 -> R.drawable.icon_thunderstorm_small
            in 301..600 -> R.drawable.icon_rain_small
            in 801..900 -> R.drawable.icon_partly_cloudy_small
            else -> R.drawable.icon_mostly_sunny_small
        }
    }

    fun getWeatherImage(data: Int?): Int {
        return when (data) {
            in 201..300 -> R.drawable.icon_thunderstorm_big
            in 301..600 -> R.drawable.icon_rain_big
            in 801..900 -> R.drawable.icon_partially_cloudy_big
            else -> R.drawable.icon_mostly_sunny_big
        }
    }
}