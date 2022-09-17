package com.example.weather.data.remote

import com.example.weather.data.model.GeoCodingItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val GEO_CODING_BASE_URL = "https://api.openweathermap.org/geo/1.0/"
private const val OPEN_WEATHER_MAP_ID = "5c15883bc2aa8181a1d35d6e2099ae8c"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GEO_CODING_BASE_URL)
    .build()


interface GeoCodingApiService {

    @GET("reverse")
    suspend fun getGeoCodingApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") appid: String = OPEN_WEATHER_MAP_ID,
    ): List<GeoCodingItem>

}

object GeoCodingApi {
    val GEO_CODING_RETROFIT_SERVICE : GeoCodingApiService by lazy { retrofit.create(
        GeoCodingApiService::class.java) }
}
