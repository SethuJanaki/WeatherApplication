package com.application.weather

import android.app.Application
import com.application.weather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(
                    appModule
                ))
            androidContext(this@WeatherApplication)
        }
    }
}
