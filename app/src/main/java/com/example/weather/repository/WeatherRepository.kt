package com.example.weather.repository

import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherApiService
import com.example.weather.network.WeatherResult
import retrofit2.http.Query

class WeatherRepository {

    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") unit: String
    ): WeatherResult {
         return WeatherApi.RETROFIT_SERVICE.getWeatherApi(lat, lon, appid, unit)
    }
}