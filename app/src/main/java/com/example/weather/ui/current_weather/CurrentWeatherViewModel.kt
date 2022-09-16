package com.example.weather.ui.current_weather


import androidx.lifecycle.*
import com.example.weather.network.Current
import kotlinx.coroutines.flow.*



class CurrentWeatherViewModel : ViewModel() {

    private val _currentWeather: MutableStateFlow<Current?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    fun displayCurrentWeather(current: Current?) {
        _currentWeather.value = current
    }

}

private const val TAG = "CurrentWeatherViewModel"
