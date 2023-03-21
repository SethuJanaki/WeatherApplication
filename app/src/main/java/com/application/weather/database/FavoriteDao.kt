package com.application.weather.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getFavorite(): Observable<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite where name = :name")
    fun getFavorite(name: String): Observable<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(news: FavoriteEntity): Completable

    @Query("DELETE FROM favorite where name = :name")
    fun deleteFavourite(name: String): Completable

    @Query("DELETE FROM favorite")
    fun deleteFavorite(): Completable
}
