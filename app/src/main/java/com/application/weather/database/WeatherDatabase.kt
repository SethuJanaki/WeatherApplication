package com.application.weather.database

import android.content.Context
import androidx.room.*

import io.reactivex.Completable
import io.reactivex.Observable

@Database(entities = [WeatherEntity::class, FavoriteEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun getInstance(context: Context): WeatherDatabase {
            return Room.databaseBuilder(
                context,
                WeatherDatabase::class.java, "database"
            ).build()
        }
    }

    fun getWeather(): Observable<List<WeatherEntity>> {
        return weatherDao().getWeather()
    }

    fun insertWeather(weather: WeatherEntity): Completable {
        return weatherDao().insertWeather(weather)
    }

    fun deleteWeather(): Completable {
        return weatherDao().deleteWeather()
    }

    fun getFavorite(): Observable<List<FavoriteEntity>> {
        return favoriteDao().getFavorite()
    }

    fun getFavorite(name: String): Observable<List<FavoriteEntity>> {
        return favoriteDao().getFavorite(name)
    }

    fun insertFavourite(weather: FavoriteEntity): Completable {
        return favoriteDao().insertFavorite(weather)
    }

    fun deleteFavorite(name: String): Completable {
        return favoriteDao().deleteFavourite(name)
    }

    fun deleteFavorites(): Completable {
        return favoriteDao().deleteFavorite()
    }
}