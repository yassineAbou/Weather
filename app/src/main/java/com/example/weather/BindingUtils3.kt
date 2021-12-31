package com.example.weather

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weather.network.Daily
import com.example.weather.network.Hourly

@BindingAdapter("daily_date")
fun TextView.setDailyDateFormatted(item: Daily) {
    item?.let {
        text = convertToDay(item.dt)
    }
}


@BindingAdapter("daily_temperature_one")
fun TextView.setDailyOneTemperature(item: Daily) {
    item?.let {
        text = "${item.temp.max.toInt()}°"
    }
}

@BindingAdapter("daily_temperature_two")
fun TextView.setDailtTwoTemperature(item: Daily) {
    item?.let {
        text = "${item.temp.min.toInt()}°"
    }
}

@BindingAdapter("daily_description")
fun TextView.setDailyDescription(item: Daily) {
    item?.let {
        text = "${item.weather[0].description}"
    }
}


@BindingAdapter("daily_img")
fun ImageView.setDailyWeatherImage(item: Daily) {
    item?.let {
        bindImage(
            imgView = this,
            imgUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
        )
    }

}