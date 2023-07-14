package com.yassineabou.weather.ui.listLocations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.data.model.Location
import com.yassineabou.weather.databinding.LocationItemBinding

class ListLocationsAdapter(private val locationActions: LocationActions) :
    ListAdapter<Location, ListLocationsAdapter.ViewHolder>(PlaceItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)!!
        holder.bind(location, locationActions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val locationItemBinding: LocationItemBinding) :
        RecyclerView.ViewHolder(locationItemBinding.root) {

        fun bind(
            location: Location,
            locationActions: LocationActions
        ) {
            locationItemBinding.apply {
                this.location = location
                this.locationActions = locationActions
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val cityItemBinding = LocationItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(cityItemBinding)
            }
        }
    }
}

class PlaceItemDiffCallback : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}

interface LocationActions {
    fun delete(location: Location)
    fun select(location: Location)
}
