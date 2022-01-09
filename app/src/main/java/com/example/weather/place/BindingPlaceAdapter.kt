package com.example.weather.place

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weather.R

@BindingAdapter("locality")
fun TextView.setDateFormatted(item: PlaceItem) {
    item?.let {
        text = item.locality
    }
}


@BindingAdapter("currentTime")
fun TextView.setTemperature(item: PlaceItem) {
    item?.let {
        text = item.time
    }
}


@BindingAdapter("delete")
fun ImageView.seWeatherImage(item: PlaceItem) {
    item?.let {
        setImageResource(
            when (item.isAutoLocation) {
                true -> R.drawable.ic_baseline_gps_fixed_24
                false -> R.drawable.ic_baseline_close_24
            }
        )
    }

}

