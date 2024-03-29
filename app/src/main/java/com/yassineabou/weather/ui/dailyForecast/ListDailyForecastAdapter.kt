package com.yassineabou.weather.ui.dailyForecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.data.model.Daily
import com.yassineabou.weather.databinding.DailyForecastItemBinding

class ListDailyForecastAdapter :
    ListAdapter<Daily, ListDailyForecastAdapter.ViewHolder>(DailyForecastDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyForecast = getItem(position)!!
        holder.bind(dailyForecast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val dailyForecastItemBinding: DailyForecastItemBinding
    ) :
        RecyclerView.ViewHolder(dailyForecastItemBinding.root) {

        fun bind(daily: Daily) {
            dailyForecastItemBinding.apply {
                dailyForecast = daily
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DailyForecastItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DailyForecastDiffCallback : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }
}
