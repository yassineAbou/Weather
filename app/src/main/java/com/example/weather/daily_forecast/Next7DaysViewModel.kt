package com.example.weather.daily_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.network.Daily

class Next7DaysViewModel : ViewModel() {

    private val _dailyItems = MutableLiveData<List<Daily>>()

    val dailyItems: LiveData<List<Daily>>
        get() = _dailyItems

    fun displayDailyWeather(daily: List<Daily>) {
        _dailyItems.value = daily
    }
}