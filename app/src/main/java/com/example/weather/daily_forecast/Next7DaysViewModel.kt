package com.example.weather.daily_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.network.Daily
import com.example.weather.network.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Next7DaysViewModel : ViewModel() {

    /*
    private val _dailyItems = MutableLiveData<List<Daily>>()
    val dailyItems: LiveData<List<Daily>>
        get() = _dailyItems

     */

    private val _dailyItems: MutableStateFlow<List<Daily>?> = MutableStateFlow(null)
    val dailyItems = _dailyItems.asStateFlow()

    fun displayDailyWeather(daily: List<Daily>) {
        _dailyItems.value = daily
    }
}