package com.example.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("locality")
fun TextView.setHourlyDateFormatted(item: PlaceItem) {
    item?.let {
        text = item.locality
    }
}


@BindingAdapter("currentTime")
fun TextView.setHourlyTemperature(item: PlaceItem) {
    item?.let {
        text = item.time
    }
}


@BindingAdapter("delete")
fun ImageView.setHourlyWeatherImage(item: PlaceItem) {
    item?.let {
        setImageResource(
            when (item.isAutoLocation) {
                true -> R.drawable.ic_baseline_gps_fixed_24
                false -> R.drawable.ic_baseline_close_24
            }
        )
    }

}

