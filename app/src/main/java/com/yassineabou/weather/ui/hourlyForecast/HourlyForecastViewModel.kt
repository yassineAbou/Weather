package com.yassineabou.weather.ui.hourlyForecast

import androidx.lifecycle.ViewModel
import com.yassineabou.weather.data.model.Hourly
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HourlyForecastViewModel : ViewModel() {

    private val _listHourlyForecast: MutableStateFlow<List<Hourly>?> = MutableStateFlow(null)
    val listHourlyForecast = _listHourlyForecast.asStateFlow()

    fun updateListHourlyForecast(listHourly: List<Hourly>?) {
        _listHourlyForecast.value = listHourly
    }
}
