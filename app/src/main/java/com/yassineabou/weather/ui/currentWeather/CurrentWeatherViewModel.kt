package com.yassineabou.weather.ui.currentWeather

import androidx.lifecycle.ViewModel
import com.yassineabou.weather.data.model.Current
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrentWeatherViewModel : ViewModel() {

    private val _currentWeather: MutableStateFlow<Current?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    fun updateCurrentWeather(current: Current?) {
        _currentWeather.value = current
    }
}
