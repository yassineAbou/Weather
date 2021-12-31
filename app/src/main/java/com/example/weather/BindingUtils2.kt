package com.example.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weather.network.Hourly

@BindingAdapter("hourly_date")
fun TextView.setHourlyDateFormatted(item: Hourly) {
    item?.let {
        text = convertToHour(item.dt)
    }
}


@BindingAdapter("hourly_temperature")
fun TextView.setHourlyTemperature(item: Hourly) {
    item?.let {
        text = "${item.temp.toInt()}°"
    }
}


@BindingAdapter("hourly_img")
fun ImageView.setHourlyWeatherImage(item: Hourly) {
    item?.let {
        bindImage(
            imgView = this,
            imgUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
        )
    }

}