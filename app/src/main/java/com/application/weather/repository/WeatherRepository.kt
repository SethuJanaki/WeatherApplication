package com.application.weather.repository

import com.application.weather.data.WeatherData
import com.application.weather.database.FavoriteEntity
import com.application.weather.network.WeatherService

import com.application.weather.database.WeatherDatabase
import com.application.weather.database.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Observable

class WeatherRepository(
    private var weatherDatabase: WeatherDatabase,
    private var weatherService: WeatherService
) {

    fun getWeather(latitude: String, longitude: String): Observable<WeatherData> {
        return weatherService.getWeather(latitude, longitude)
    }

    fun getWeather(query: String): Observable<WeatherData> {
        return weatherService.getWeather(query)
    }

    fun getFavourite(): Observable<List<FavoriteEntity>> {
        return weatherDatabase.getFavorite()
    }

    fun getFavourite(name: String): Observable<List<FavoriteEntity>> {
        return weatherDatabase.getFavorite(name)
    }

    fun insertFavorite(weather: FavoriteEntity): Completable {
        return weatherDatabase.insertFavourite(weather)
    }

    fun deleteFavorite(name: String): Completable {
        return weatherDatabase.deleteFavorite(name)
    }

    fun deleteFavorites(): Completable {
        return weatherDatabase.deleteFavorites()
    }

    fun insertWeather(weather: WeatherEntity): Completable {
        return weatherDatabase.insertWeather(weather)
    }

    fun getWeather(): Observable<List<WeatherEntity>> {
        return weatherDatabase.getWeather()
    }

    fun deleteWeather(): Completable {
        return weatherDatabase.deleteWeather()
    }

}