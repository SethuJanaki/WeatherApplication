package com.application.weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.weather.data.Weather
import com.application.weather.databinding.LayoutWeatherBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class RecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: List<Weather> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        (holder as WeatherViewHolder).bind(item)
    }

    override fun getItemCount() = itemsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(
            LayoutWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class WeatherViewHolder(
        var binding: LayoutWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.title.text = weather.name
            binding.description.text = weather.value
            binding.weatherIcon.setImageResource(weather.id)
        }
    }

}