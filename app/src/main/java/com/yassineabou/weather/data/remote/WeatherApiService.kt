package com.yassineabou.weather.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yassineabou.weather.data.model.WeatherResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//Update the base URL to the new API
private const val WEATHER_BASE_URL = "https://api.open-meteo.com/v1/"

// Initialize Moshi for JSON serialization/deserialization
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Create a Retrofit instance
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_BASE_URL)
    .build()

// Define the WeatherApiService interface to access the new API
interface WeatherApiService {

    @GET("forecast")
    suspend fun getWeatherApi(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,is_day,weather_code,wind_speed_10m",
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "GMT"
    ): WeatherResult
}

// Create a singleton object to access the WeatherApiService
object WeatherApi {
    val WEATHER_RETROFIT_SERVICE: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}