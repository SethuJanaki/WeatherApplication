package com.application.weather.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.weather.data.WeatherData
import com.application.weather.database.FavoriteEntity
import com.application.weather.database.WeatherEntity
import com.application.weather.repository.LocationRepository
import com.application.weather.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val locationLiveData: LiveData<Location?>
        get() = locationRepository.locationLiveData

    private val _weatherLiveData = MutableLiveData<WeatherData>()
    val weatherLiveData: LiveData<WeatherData>
        get() = _weatherLiveData

    private val _favoriteLiveData = MutableLiveData<Boolean>()
    val favoriteLiveData: LiveData<Boolean>
        get() = _favoriteLiveData

    private val compositeDisposable = CompositeDisposable()

    fun getLocation() {
        locationRepository.getCurrentLocation()
    }

    fun getWeather(lat: String, lon: String) {
        weatherRepository.getWeather(lat, lon).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherLiveData.value = it
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun getWeather(name: String) {
        weatherRepository.getWeather(name).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                _weatherLiveData.value = it
            }, {
            }).addTo(compositeDisposable)
    }

    fun getFavorite(name: String) {
        weatherRepository.getFavourite(name).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _favoriteLiveData.value = it.isNotEmpty()
            }
            .addTo(compositeDisposable)
    }

    fun deleteFavorite(weather: WeatherData) {
        weatherRepository.deleteFavorite(weather.name.orEmpty()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteLiveData.value = false
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun insertWeather(weather: WeatherData) {
        weatherRepository.insertWeather(
            WeatherEntity(
                name = weather.name.toString(),
                id = weather.weather?.getOrNull(0)?.id,
                temperature = weather.main?.temp,
                description = weather.weather?.getOrNull(0)?.description,
                latitude = weather.coord?.lat,
                longitude = weather.coord?.lon,
                favorite = weather.favorite
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .addTo(compositeDisposable)
    }

    fun insertFavourite(weather: WeatherData) {
        weatherRepository.insertFavorite(
            FavoriteEntity(
                name = weather.name.toString(),
                id = weather.weather?.getOrNull(0)?.id,
                temperature = weather.main?.temp,
                description = weather.weather?.getOrNull(0)?.description,
                latitude = weather.coord?.lat,
                longitude = weather.coord?.lon,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .addTo(compositeDisposable)
    }
}
