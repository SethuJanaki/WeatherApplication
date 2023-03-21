package com.application.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getWeather(): Observable<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(news: WeatherEntity): Completable

    @Query("DELETE FROM weather")
    fun deleteWeather(): Completable
}
