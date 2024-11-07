package com.yassineabou.weather.ui.hourlyForecast

import androidx.lifecycle.ViewModel
import com.yassineabou.weather.data.model.Hourly
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class HourlyForecastViewModel : ViewModel() {

    private val _listHourlyForecast: MutableStateFlow<List<HourlyForecastDataHolder>?> = MutableStateFlow(null)
    val listHourlyForecast = _listHourlyForecast.asStateFlow()

    fun updateListHourlyForecast(hourly: Hourly?) {
        val first48Hours = hourly?.time?.take(48)
        val hourlyDataHolders = first48Hours?.mapIndexed { index, time ->
            HourlyForecastDataHolder(
                time = time.substringAfter('T'),
                temperature2m = hourly.temperature2m[index],
                weatherCode = hourly.weatherCode[index]
            )
        }
        _listHourlyForecast.value = hourlyDataHolders
    }
}


data class HourlyForecastDataHolder(
    val id: String = UUID.randomUUID().toString(),
    val time: String,
    val temperature2m: Double,
    val weatherCode: Long,
)
