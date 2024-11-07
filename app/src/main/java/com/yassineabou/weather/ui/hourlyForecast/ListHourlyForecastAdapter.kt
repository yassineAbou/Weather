package com.yassineabou.weather.ui.hourlyForecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.databinding.HourlyForecastItemBinding

class ListHourlyForecastAdapter :
    ListAdapter<HourlyForecastDataHolder, ListHourlyForecastAdapter.ViewHolder>(HourlyForecastDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyForecast = getItem(position)!!
        holder.bind(hourlyForecast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val hourlyForecastItemBinding: HourlyForecastItemBinding
    ) :
        RecyclerView.ViewHolder(hourlyForecastItemBinding.root) {

        fun bind(hourlyForecastDataHolder: HourlyForecastDataHolder) {
            hourlyForecastItemBinding.apply {
                this.hourlyForecastDataHolder = hourlyForecastDataHolder
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HourlyForecastItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HourlyForecastDiffCallback : DiffUtil.ItemCallback<HourlyForecastDataHolder>() {
    override fun areItemsTheSame(oldItem: HourlyForecastDataHolder, newItem: HourlyForecastDataHolder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HourlyForecastDataHolder, newItem: HourlyForecastDataHolder): Boolean {
        return oldItem == newItem
    }
}
