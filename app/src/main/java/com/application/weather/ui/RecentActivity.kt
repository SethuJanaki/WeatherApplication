package com.application.weather.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.application.weather.data.WeatherData
import com.application.weather.database.FavoriteEntity
import com.application.weather.database.WeatherEntity
import com.application.weather.databinding.RecentActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecentActivity : AppCompatActivity() {

    private lateinit var binding: RecentActivityBinding
    private val viewModel by viewModel<RecentViewModel>()
    var weather: List<WeatherEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel.getWeather()

        viewModel.weatherLiveData.observe(this) { data ->
            if (data.isEmpty()) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                binding.emptyLayout.visibility = View.GONE
            }
            this.weather = data
            viewModel.getFavorite()
        }
        viewModel.favoriteLiveData.observe(this) { favorite ->
            weather?.forEach { weather ->
                weather.favorite = favorite.any { weather.name == it.name }
            }
            binding.recyclerview.adapter = RecentAdapter().apply {
                itemsList = weather ?: emptyList()
                clickListener = {
                    if (it.favorite == true) {
                        viewModel.deleteFavorite(it.name)
                    } else {
                        viewModel.insertFavourite(it)
                    }
                }
            }
        }
        binding.back.setOnClickListener {
            finish()
        }
        binding.remove.setOnClickListener {
            viewModel.deleteWeather()
        }
    }
}