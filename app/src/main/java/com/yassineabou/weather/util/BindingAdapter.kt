package com.yassineabou.weather.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yassineabou.weather.R
import com.yassineabou.weather.data.model.Daily
import com.yassineabou.weather.data.model.Hourly
import com.yassineabou.weather.data.model.Location

// ------------------------
// location_item.xml
// ------------------------

@BindingAdapter("locality")
fun TextView.setDateFormatted(item: Location) {
    text = item.locality
}

@BindingAdapter("Icon")
fun ImageView.setWeatherImage(item: Location) {
    setImageResource(
        when (item.isAutoLocation) {
            true -> R.drawable.ic_baseline_gps_fixed_24
            false -> R.drawable.ic_baseline_close_24
        }
    )
}

// ----------------------------
// hourly_forecast_item.xml
// ----------------------------

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
    this.setImage(hourly.weather[0].icon)
}

// --------------------
// daily_forecast_item.xml
// --------------------

@BindingAdapter("day")
fun TextView.setDay(daily: Daily) {
    text = convertDateToDay(daily.dt)
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
    this.setImage(daily.weather[0].icon)
}
