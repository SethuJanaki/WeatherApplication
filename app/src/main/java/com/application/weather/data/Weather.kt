package com.application.weather.data

import androidx.annotation.DrawableRes
import com.application.weather.R
import com.application.weather.util.DataUtils

class Weather(
    @DrawableRes
    var id: Int = 0,
    var name: String? = null,
    var value: String? = null,
) {

    companion object {

        fun getWeatherDataList(weatherData: WeatherData): List<Weather> {
            return listOf(
                Weather(
                    R.drawable.icon_temperature_info,
                    "Min - Max",
                    DataUtils.getTemperature(
                        weatherData.main?.temp_min?.toDouble() ?: 0.0,
                        weatherData.main?.temp_max?.toDouble() ?: 0.0
                    )
                ),
                Weather(
                    R.drawable.icon_precipitation_info,
                    "Precipitation",
                    weatherData.rain?.data ?: "0"
                ),
                Weather(
                    R.drawable.icon_humidity_info,
                    "Humidity",
                    weatherData.main?.humidity.orEmpty()
                ),
                Weather(
                    R.drawable.icon_wind_info,
                    "Wind",
                    weatherData.wind?.speed.orEmpty()
                ),
                Weather(
                    R.drawable.icon_visibility_info,
                    "Visibility",
                    weatherData.visibility.orEmpty()
                )
            )
        }
    }
}



