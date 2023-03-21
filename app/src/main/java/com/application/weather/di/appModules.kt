package com.application.weather.di

import com.application.weather.database.WeatherDatabase
import com.application.weather.network.WeatherService
import com.application.weather.repository.LocationRepository
import com.application.weather.repository.WeatherRepository
import com.application.weather.ui.MainViewModel
import com.application.weather.ui.RecentViewModel
import com.application.weather.ui.FavoriteViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    factory { provideNewsClient(get()) }

    single { provideRetrofit() }

    single {
        WeatherDatabase.getInstance(androidApplication())
    }

    single {
        WeatherRepository(get(), get())
    }

    single {
        LocationRepository(androidApplication())
    }

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel(get())
    }

    viewModel {
        RecentViewModel(get())
    }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideHttpClient(): OkHttpClient {
    val httpInterceptor = HttpLoggingInterceptor()
    httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient()
    client.interceptors.plus(httpInterceptor)
    return client
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.openweathermap.org")
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
}

fun provideNewsClient(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

