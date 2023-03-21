package com.application.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "latitude") var latitude: String?,
    @ColumnInfo(name = "longitude") var longitude: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "temperature") var temperature: String?,
    @ColumnInfo(name = "favorite") var favorite: Boolean?,
)