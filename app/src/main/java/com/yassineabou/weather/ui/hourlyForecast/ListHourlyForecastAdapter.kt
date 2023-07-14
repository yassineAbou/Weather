package com.yassineabou.weather.ui.hourlyForecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.data.model.Hourly
import com.yassineabou.weather.databinding.HourlyForecastItemBinding

class ListHourlyForecastAdapter :
    ListAdapter<Hourly, ListHourlyForecastAdapter.ViewHolder>(HourlyForecastDiffCallback()) {

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

        fun bind(hourly: Hourly) {
            hourlyForecastItemBinding.apply {
                this.hourlyForecast = hourly
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

class HourlyForecastDiffCallback : DiffUtil.ItemCallback<Hourly>() {
    override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }
}
