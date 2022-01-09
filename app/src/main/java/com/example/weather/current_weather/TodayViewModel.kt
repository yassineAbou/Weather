package com.example.weather.current_weather

import android.app.Application
import androidx.lifecycle.*
import com.example.weather.R
import com.example.weather.network.Current


class TodayViewModel : ViewModel() {

    private val _current = MutableLiveData<Current> ()
    val current: LiveData<Current>
        get() = _current

    fun displayCurrentWeather(currentWeather: Current) {
        _current.value = currentWeather
    }


    val temperature = Transformations.map(current) {
        "${it.temp.toInt()}°"
    }

    val humidity = Transformations.map(current) {
        "${it.humidity}%"
    }

    val wind = Transformations.map(current) {
        "${it.wind_speed}m/s"
    }


    val currentImage = Transformations.map(current) {
         it.weather[0].icon
    }




}