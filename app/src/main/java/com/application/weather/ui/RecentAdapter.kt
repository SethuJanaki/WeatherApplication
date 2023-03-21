package com.application.weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.weather.R
import com.application.weather.database.WeatherEntity
import com.application.weather.databinding.LayoutFavoriteBinding
import com.application.weather.databinding.LayoutRecentBinding
import com.application.weather.util.DataUtils
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class RecentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: List<WeatherEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

     var clickListener: ((WeatherEntity) -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        (holder as WeatherViewHolder).bind(item)
    }

    override fun getItemCount() = itemsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(
            LayoutRecentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

   inner class WeatherViewHolder(
        var binding: LayoutRecentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherEntity) {
            binding.title.text = weather.name
            binding.temperature.text = binding.root.resources.getString(
                R.string.temperature,
                weather.temperature?.toDouble()?.let {
                    DataUtils.tempToCelsius(it)
                })
            binding.description.text = weather.description
            binding.weatherIcon.setImageResource(DataUtils.getImage(weather.id))
            if(weather.favorite == true) {
                binding.iconFavorite.setImageResource(R.drawable.icon_favourite_active)
            }else {
                binding.iconFavorite.setImageResource(R.drawable.icon_favourite)
            }
            binding.iconFavorite.setOnClickListener {
                clickListener?.invoke(weather)
            }
        }
    }

}