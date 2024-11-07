package com.yassineabou.weather.util

import android.health.connect.datatypes.units.Temperature
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yassineabou.weather.R
import com.yassineabou.weather.data.model.Daily
import com.yassineabou.weather.data.model.Hourly
import com.yassineabou.weather.data.model.Location
import com.yassineabou.weather.ui.dailyForecast.DailyForecastDataHolder
import com.yassineabou.weather.ui.hourlyForecast.HourlyForecastDataHolder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
fun TextView.setHour(time: String) {
    text = time
}

@BindingAdapter("hourly_temperature")
fun TextView.setHourlyTemperature(temperature2m: Double)  {
    text = "${temperature2m.toInt()}°"
}


@BindingAdapter("image_hourly_forecast")
fun ImageView.setImageHourlyForecast(weatherCode: Long) {
    val weatherIcon = getWeatherDetails(weatherCode.toInt()).first
    this.setImageResource(weatherIcon)
}



// --------------------
// daily_forecast_item.xml
// --------------------

@BindingAdapter("day")
fun TextView.setDay(time: String) {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = formatter.parse(time)
    val calendar = java.util.Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.getDisplayName(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.LONG, Locale.getDefault())
    text = dayOfWeek
}

@BindingAdapter("daily_max_temperature")
fun TextView.setDailyMaxTemperature(temperature2mMax: Double) {
    text = "${temperature2mMax.toInt()}°"
}

@BindingAdapter("daily_min_temperature")
fun TextView.setDailyMinTemperature(temperature2mMin: Double) {
    text = "${temperature2mMin.toInt()}°"
}

@BindingAdapter("daily_weather_description")
fun TextView.setDailyWeatherDescription(weatherCode: Long) {
    val weatherDescription = getWeatherDetails(weatherCode.toInt()).second
    text = weatherDescription
}

@BindingAdapter("image_daily_forecast")
fun ImageView.setImageDailyForecast(weatherCode: Long) {
    val weatherIcon = getWeatherDetails(weatherCode.toInt()).first
    this.setImageResource(weatherIcon)
}
