package com.yassineabou.weather.ui.dailyForecast

import androidx.lifecycle.ViewModel
import com.yassineabou.weather.data.model.Daily
import com.yassineabou.weather.ui.hourlyForecast.HourlyForecastDataHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class DailyForecastViewModel : ViewModel() {

    private val _listDailyForecast: MutableStateFlow<List<DailyForecastDataHolder>?> = MutableStateFlow(null)
    val listDailyForecast = _listDailyForecast.asStateFlow()

    fun updateListDailyForecast(daily: Daily?) {
        val dailyForecastDataHolder = daily?.time?.mapIndexed { index, time ->
            DailyForecastDataHolder(
                time = time,
                weatherCode = daily.weatherCode[index],
                temperature2mMax = daily.temperature2mMax[index],
                temperature2mMin = daily.temperature2mMin[index],
            )
        }
        _listDailyForecast.value =dailyForecastDataHolder
    }
}


data class DailyForecastDataHolder(
    val id: String = UUID.randomUUID().toString(),
    val time: String,
    val weatherCode: Long,
    val temperature2mMax: Double,
    val temperature2mMin: Double,
)
