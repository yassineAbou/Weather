package com.example.weather.ui.dailyForecast

import androidx.lifecycle.ViewModel
import com.example.weather.data.model.Daily
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DailyForecastViewModel : ViewModel() {

    private val _listDailyForecast: MutableStateFlow<List<Daily>?> = MutableStateFlow(null)
    val listDailyForecast = _listDailyForecast.asStateFlow()

    fun updateListDailyForecast(listDaily: List<Daily>?) {
        _listDailyForecast.value = listDaily
    }
}
