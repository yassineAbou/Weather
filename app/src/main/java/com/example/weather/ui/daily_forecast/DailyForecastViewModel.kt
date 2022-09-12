package com.example.weather.ui.daily_forecast

import androidx.lifecycle.ViewModel
import com.example.weather.network.Daily
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DailyForecastViewModel : ViewModel() {

    private val _listDailyForecast: MutableStateFlow<List<Daily>?> = MutableStateFlow(null)
    val listDailyForecast = _listDailyForecast.asStateFlow()

    fun displayDailyForecast(listDaily: List<Daily>?) {
        _listDailyForecast.value = listDaily
    }
}