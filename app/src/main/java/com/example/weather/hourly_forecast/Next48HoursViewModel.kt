package com.example.weather.hourly_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.network.Hourly

class Next48HoursViewModel : ViewModel() {


    private val _hourlyItems = MutableLiveData<List<Hourly>>()

    val hourlyItems: LiveData<List<Hourly>>
        get() = _hourlyItems

    fun displayHourlyWeather(hourly: List<Hourly>) {
        _hourlyItems.value = hourly
    }

}