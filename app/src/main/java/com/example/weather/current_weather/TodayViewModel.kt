package com.example.weather.current_weather

import android.app.Application
import androidx.lifecycle.*
import com.example.weather.R
import com.example.weather.network.Current
import com.example.weather.network.WeatherResult
import com.example.weather.util.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*



class TodayViewModel : ViewModel() {

    /*
    private val _current = MutableLiveData<Current> ()
    val current: LiveData<Current>
        get() = _current

     */

    private val _current: MutableStateFlow<Current?> = MutableStateFlow(null)
    val current = _current.asStateFlow()

    fun displayCurrentWeather(currentWeather: Current) {
        _current.value = currentWeather
    }


    val temperature = _current.map(viewModelScope) {
        it?.let {
            "${it.temp.toInt()}°"
        }
    }

    val humidity = _current.map(viewModelScope) {
        it?.let {
            "${it.humidity}%"
        }
    }

    val wind = _current.map(viewModelScope) {
        it?.let {
            "${it.wind_speed}m/s"
        }
    }


    val currentImage = _current.map(viewModelScope) {
        it?.let {
            it.weather[0].icon
        }
    }







}