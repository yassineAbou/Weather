package com.yassineabou.weather.ui.dailyForecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.data.model.Daily
import com.yassineabou.weather.databinding.DailyForecastItemBinding

class ListDailyForecastAdapter :
    ListAdapter<DailyForecastDataHolder, ListDailyForecastAdapter.ViewHolder>(DailyForecastDiffCallback()) {

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

        fun bind(dailyForecastDataHolder: DailyForecastDataHolder) {
            dailyForecastItemBinding.apply {
                this.dailyForecastDataHolder = dailyForecastDataHolder
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

class DailyForecastDiffCallback : DiffUtil.ItemCallback<DailyForecastDataHolder>() {
    override fun areItemsTheSame(oldItem: DailyForecastDataHolder, newItem: DailyForecastDataHolder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DailyForecastDataHolder, newItem: DailyForecastDataHolder): Boolean {
        return oldItem == newItem
    }
}
