package com.application.weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.weather.databinding.LayoutSearchBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
     var clickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        (holder as WeatherViewHolder).bind(item)
    }

    override fun getItemCount() = itemsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeatherViewHolder(
            LayoutSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

   inner class WeatherViewHolder(
        var binding: LayoutSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.title.text = title
            binding.root.setOnClickListener {
                clickListener?.invoke(title)
            }
        }
    }

}