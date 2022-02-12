package com.example.weather.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.PlaceItemBinding
import com.example.weather.database.PlaceItem


class PlaceItemsAdapter(
    private val clickListener: PlaceItemListener,
    private val clickListener2: PlaceItemListener2) :
    ListAdapter<PlaceItem, PlaceItemsAdapter.ViewHolder>(PlaceItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListener, clickListener2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: PlaceItem,
            clickListener: PlaceItemListener,
            clickListener2: PlaceItemListener2
        ) {
            binding.apply {
                placeItem = item
                clickListenerBind = clickListener
                clickListenerBind2 = clickListener2
                executePendingBindings()

            }


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlaceItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }




}

class PlaceItemDiffCallback : DiffUtil.ItemCallback<PlaceItem>() {

    override fun areItemsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
        return oldItem == newItem
    }
}

class PlaceItemListener(val clickListener: (place: PlaceItem) -> Unit) {
    fun onClick(placeItem: PlaceItem) = clickListener(placeItem)
}

class PlaceItemListener2(val clickListener2: (place2: PlaceItem) -> Unit) {
    fun onClick2(placeItem2: PlaceItem) = clickListener2(placeItem2)
}




