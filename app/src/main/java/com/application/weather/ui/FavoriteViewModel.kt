package com.application.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.weather.database.FavoriteEntity
import com.application.weather.database.WeatherEntity
import com.application.weather.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<List<FavoriteEntity>?>()
    val weatherLiveData: LiveData<List<FavoriteEntity>?>
        get() = _weatherLiveData

    private val compositeDisposable = CompositeDisposable()

    fun getFavorite() {
        weatherRepository.getFavourite().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherLiveData.value = it
            }, {
                println(it.message)
            })
            .addTo(compositeDisposable)
    }

    fun deleteFavorite(name: String) {
        weatherRepository.deleteFavorite(name).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{}
            .addTo(compositeDisposable)
    }

    fun deleteFavorites() {
        weatherRepository.deleteFavorites().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{}
            .addTo(compositeDisposable)
    }
}
