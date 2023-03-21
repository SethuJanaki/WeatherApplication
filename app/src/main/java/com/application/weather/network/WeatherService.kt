package com.application.weather.network

import com.application.weather.data.WeatherData
import io.reactivex.Observable
import retrofit2.http.*


interface WeatherService {

    @GET("/data/2.5/weather?APPID=39f9f747d649bbcbaf77e622efce6d65")
    fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String):  Observable<WeatherData>

    @GET("/data/2.5/weather?APPID=39f9f747d649bbcbaf77e622efce6d65")
    fun getWeather(@Query("q") query: String): Observable<WeatherData>

}