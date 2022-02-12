package com.example.weather.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.daily_forecast.DailyItemsAdapter
import com.example.weather.hourly_forecast.HourlyItemsAdapter
import com.example.weather.network.Daily
import com.example.weather.network.Hourly
import com.example.weather.database.PlaceItem

//------------------------
// Place
//------------------------

@BindingAdapter("locality")
fun TextView.setDateFormatted(item: PlaceItem) {
    item?.let {
        text = item.locality
    }
}
/*
@BindingAdapter("currentWeather")
fun TextView.currentWeather(item: PlaceItem) {
    item?.let {
        text = item.currentWeather
    }
}

 */

@BindingAdapter("Icon")
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

//------------------------
// fragment_today_xml
//------------------------

@BindingAdapter("imageUrl")
fun bindTodayImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .into(imgView)
    }
}

//----------------------------
// fragment_next48_hours_xml
//----------------------------

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
            imgUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        )
    }

}

@BindingAdapter("hourly_listData")
fun bindHourlyRecyclerView(recyclerView: RecyclerView, hourly: List<Hourly>?) {
    val adapter = recyclerView.adapter as HourlyItemsAdapter
    adapter.submitList(hourly)
}

//--------------------
// fragment_next7_days
//--------------------

@BindingAdapter("daily_listData")
fun bindDailyRecyclerView(recyclerView: RecyclerView, daily: List<Daily>?) {
    val adapter = recyclerView.adapter as DailyItemsAdapter
    adapter.submitList(daily)
}

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
            imgUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        )
    }

}


