package com.application.weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.weather.R
import com.application.weather.database.FavoriteEntity
import com.application.weather.databinding.LayoutFavoriteBinding
import com.application.weather.util.DataUtils
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class FavoriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: List<FavoriteEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

     var clickListener: ((FavoriteEntity) -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        (holder as WeatherViewHolder).bind(item)
    }

    override fun getItemCount() = itemsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(
            LayoutFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

   inner class WeatherViewHolder(
        var binding: LayoutFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: FavoriteEntity) {
            binding.title.text = weather.name
            binding.temperature.text = binding.root.resources.getString(R.string.temperature,
                weather.temperature?.toDouble()?.let {
                DataUtils.tempToCelsius(it)
            })
            binding.description.text = weather.description
            binding.weatherIcon.setImageResource(DataUtils.getImage(weather.id))
            binding.iconFavorite.setOnClickListener {
                clickListener?.invoke(weather)
            }
        }
    }

}