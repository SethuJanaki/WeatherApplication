package com.application.weather.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.application.weather.R
import com.application.weather.data.Weather
import com.application.weather.data.WeatherData
import com.application.weather.databinding.MainActivityBinding
import com.google.android.material.navigation.NavigationView
import com.application.weather.util.DataUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel by viewModel<MainViewModel>()
    var weather: WeatherData? = null

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
            ) || permissions.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false
            )
        ) {
            viewModel.getLocation()
        }
    }

    private val activityRequest = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { results ->
        val name = results.data?.getStringExtra("City") ?: ""
        viewModel.getWeather(name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            viewModel.getLocation()
        }

        viewModel.locationLiveData.observe(this) {
            viewModel.getWeather(it?.latitude.toString(), it?.longitude.toString())
        }

        viewModel.favoriteLiveData.observe(this) { favorite ->
            if (favorite) {
                weather?.favorite = true
                binding.iconFavorite.setImageResource(R.drawable.icon_favourite_active)
            } else {
                weather?.favorite = false
                binding.iconFavorite.setImageResource(R.drawable.icon_favourite)
            }
        }

        viewModel.weatherLiveData.observe(this) { data ->
            this.weather = data
            viewModel.insertWeather(data)
            viewModel.getFavorite(data.name.orEmpty())
            binding.apply {
                date.text = DataUtils.getDate(data.dt?.toLong() ?: 0)
                location.text = String.format("%s, %s", data.name, data.sys?.country)
                weatherIcon.setImageResource(DataUtils.getWeatherImage(data.weather?.getOrNull(0)?.id))
                temperature.text =
                    DataUtils.tempToCelsius(data.main?.temp?.toDouble() ?: 0.0)
                description.text = data.weather?.getOrNull(0)?.description
                weather.adapter = RecyclerAdapter().apply {
                    itemsList = Weather.getWeatherDataList(data)
                }
            }
            binding.iconFavorite.setOnClickListener {
                if (weather?.favorite == true) {
                    viewModel.deleteFavorite(data)
                } else {
                    viewModel.insertFavourite(data)
                }
            }
            binding.celsius.setOnClickListener {
                binding.temperature.text =
                    DataUtils.tempToCelsius(data.main?.temp?.toDouble() ?: 0.0)
                binding.celsius.setBackgroundResource(R.drawable.button_selected_drawable)
                binding.celsius.setTextColor(getColor(R.color.red))
                binding.farenheit.setBackgroundResource(R.drawable.button_unselected_drawable)
                binding.farenheit.setTextColor(getColor(R.color.white))
            }
            binding.farenheit.setOnClickListener {
                binding.temperature.text =
                    DataUtils.tempToFarenheit(data.main?.temp?.toDouble() ?: 0.0)
                binding.farenheit.setBackgroundResource(R.drawable.button_selected_drawable)
                binding.farenheit.setTextColor(getColor(R.color.red))
                binding.celsius.setBackgroundResource(R.drawable.button_unselected_drawable)
                binding.celsius.setTextColor(getColor(R.color.white))
            }
        }

        binding.search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            activityRequest.launch(intent)
        }

        binding.menu.setOnClickListener {
            drawerLayout.open()
        }

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.close()
            setSelectedItem(it)
        }
    }

    private fun setSelectedItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.search -> {
                val intent = Intent(this, RecentActivity::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, FavouriteActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}