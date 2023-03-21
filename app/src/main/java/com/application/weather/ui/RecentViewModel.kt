package com.application.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.weather.data.WeatherData
import com.application.weather.database.FavoriteEntity
import com.application.weather.database.WeatherEntity
import com.application.weather.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class RecentViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<List<WeatherEntity>>()
    val weatherLiveData: LiveData<List<WeatherEntity>>
        get() = _weatherLiveData

    private val _favoriteLiveData = MutableLiveData<List<FavoriteEntity>>()
    val favoriteLiveData: LiveData<List<FavoriteEntity>>
        get() = _favoriteLiveData


    private val compositeDisposable = CompositeDisposable()

    fun getWeather() {
        weatherRepository.getWeather().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherLiveData.value = it
            }, {
                println(it.message)
            })
            .addTo(compositeDisposable)
    }

    fun getFavorite() {
        weatherRepository.getFavourite().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteLiveData.value = it
            }, {
                println(it.message)
            })
            .addTo(compositeDisposable)
    }

    fun insertFavourite(weather: WeatherEntity) {
        weatherRepository.insertFavorite(
            FavoriteEntity(
                name = weather.name,
                id= weather.id,
                latitude = weather.latitude,
                longitude = weather.longitude,
                temperature = weather.temperature,
                description = weather.description
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .addTo(compositeDisposable)
    }

    fun deleteWeather() {
        weatherRepository.deleteWeather().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .addTo(compositeDisposable)
    }

    fun deleteFavorite(name: String) {
        weatherRepository.deleteFavorite(name).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .addTo(compositeDisposable)
    }

}
