package com.yassineabou.weather.data.repository

import com.yassineabou.weather.data.model.WeatherResult
import com.yassineabou.weather.data.remote.WeatherApi
import javax.inject.Inject
import retrofit2.http.Query

class WeatherRepository @Inject constructor() {

    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherResult {
        return WeatherApi.WEATHER_RETROFIT_SERVICE.getWeatherApi(
            lat = lat,
            lon = lon
        )
    }
}
