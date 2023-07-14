package com.yassineabou.weather.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yassineabou.weather.BuildConfig
import com.yassineabou.weather.data.model.WeatherResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_BASE_URL)
    .build()

interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeatherApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("appid") appid: String = WEATHER_API_KEY,
        @Query("units") unit: String = "metric"
    ): WeatherResult
}

object WeatherApi {
    val WEATHER_RETROFIT_SERVICE: WeatherApiService by lazy {
        retrofit.create(
            WeatherApiService::class.java
        )
    }
}
