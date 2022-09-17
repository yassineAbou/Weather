package com.example.weather.ui.hourly_forecast

import androidx.lifecycle.ViewModel
import com.example.weather.data.model.Hourly
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class HourlyForecastViewModel : ViewModel() {

    private val _listHourlyForecastFlow: MutableStateFlow<List<Hourly>?> = MutableStateFlow(null)
    val listHourlyForecastFlow = _listHourlyForecastFlow.asStateFlow()

    fun displayHourlyForecast(listHourly: List<Hourly>?) {
        _listHourlyForecastFlow.value = listHourly
    }

}