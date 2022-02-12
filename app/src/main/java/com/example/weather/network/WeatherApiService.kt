package com.example.weather.network

import com.example.weather.util.Constants.BASE_URL
import com.example.weather.util.Constants.OPENWEATHERMAP_ID
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(HTTPLogger.getLogger())
    .build()


interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeatherApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") minute: String = "minutely",
        @Query("appid") appid: String = OPENWEATHERMAP_ID,
        @Query("units") unit: String = "metric"
    ): WeatherResult
}


object WeatherApi {
    val RETROFIT_SERVICE : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}