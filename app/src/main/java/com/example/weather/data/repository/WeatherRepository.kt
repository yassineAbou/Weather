package com.example.weather.data.repository

import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherResult
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor() {

    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherResult {
         return WeatherApi.WEATHER_RETROFIT_SERVICE.getWeatherApi(
            lat =  lat,
            lon =  lon
         )
    }
}