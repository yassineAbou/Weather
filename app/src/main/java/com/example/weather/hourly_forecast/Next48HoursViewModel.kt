package com.example.weather.hourly_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.network.Hourly
import com.example.weather.network.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Next48HoursViewModel : ViewModel() {


    /*
    private val _hourlyItems = MutableLiveData<List<Hourly>>()
    val hourlyItems: LiveData<List<Hourly>>
        get() = _hourlyItems

     */

    private val _hourlyItems: MutableStateFlow<List<Hourly>?> = MutableStateFlow(null)
    val hourlyItems = _hourlyItems.asStateFlow()

    fun displayHourlyWeather(hourly: List<Hourly>) {
        _hourlyItems.value = hourly
    }

}