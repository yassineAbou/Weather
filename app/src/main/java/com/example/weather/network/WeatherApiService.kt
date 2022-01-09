package com.example.weather.network

import com.example.weather.util.Constants.BASE_URL
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
    .build()


interface WeatherApiService {
    /**
     * Returns a Coroutine [List] of [MarsProperty] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("onecall")
    suspend fun getWeatherApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") unit: String
    ): WeatherResult
}


object WeatherApi {
    val RETROFIT_SERVICE : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}