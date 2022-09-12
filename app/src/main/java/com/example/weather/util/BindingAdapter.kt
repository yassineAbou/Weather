package com.example.weather.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.network.Daily
import com.example.weather.network.Hourly
import com.example.weather.data.model.Location

//------------------------
// Place
//------------------------

@BindingAdapter("locality")
fun TextView.setDateFormatted(item: Location) {
    item?.let {
        text = item.locality
    }
}


@BindingAdapter("Icon")
fun ImageView.seWeatherImage(item: Location) {
    item?.let {
        setImageResource(
            when (item.isAutoLocation) {
                true -> R.drawable.ic_baseline_gps_fixed_24
                false -> R.drawable.ic_baseline_close_24
            }
        )
    }

}
//----------------------------
// currentWeather
//----------------------------


fun bindImageCurrentWeather(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .into(imgView)
    }
}

//----------------------------
// hourlyForecast
//----------------------------

@BindingAdapter("hour")
fun TextView.setHour(hourly: Hourly) {
      text = convertDateToHour(hourly.dt)
}


@BindingAdapter("hourly_temperature")
fun TextView.setHourlyTemperature(hourly: Hourly) {
      text = "${hourly.temp.toInt()}°"
}

@BindingAdapter("image_hourly_forecast")
fun ImageView.setImageHourlyForecast(hourly: Hourly) {
        bindImage(
            imgView = this,
            imgUrl = "https://openweathermap.org/img/wn/${hourly.weather[0].icon}@2x.png"
        )
}

//--------------------
// dailyForecast
//--------------------

@BindingAdapter("day")
fun TextView.setDay(daily: Daily) {
     text = convertToDay(daily.dt)
}

@BindingAdapter("daily_max_temperature")
fun TextView.setDailyMaxTemperature(daily: Daily) {
      text = "${daily.temp.max.toInt()}°"
}

@BindingAdapter("daily_min_temperature")
fun TextView.setDailyMinTemperature(daily: Daily) {
      text = "${daily.temp.min.toInt()}°"
}

@BindingAdapter("daily_weather_description")
fun TextView.setDailyWeatherDescription(daily: Daily) {
      text = "${daily.weather[0].description}"

}

@BindingAdapter("image_daily_forecast")
fun ImageView.setImageDailyForecast(daily: Daily) {
     bindImage(
         imgView = this,
         imgUrl = "https://openweathermap.org/img/wn/${daily.weather[0].icon}@2x.png"
     )
}


