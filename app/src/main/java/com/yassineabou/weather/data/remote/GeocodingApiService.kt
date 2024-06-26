package com.yassineabou.weather.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yassineabou.weather.data.model.Geocoding
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val GEO_CODING_BASE_URL = "https://geocode.maps.co/"
private const val CEO_CODING_API_KEY = "663ecfda36dbd060556360aikfbe9d6"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GEO_CODING_BASE_URL)
    .build()

interface GeocodingApiService {

    @GET("reverse")
    suspend fun getGeocodingApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("api_key") apiKey: String = CEO_CODING_API_KEY
    ): Geocoding
}

object GeocodingApi {
    val GEOCODING_RETROFIT_SERVICE: GeocodingApiService by lazy {
        retrofit.create(
            GeocodingApiService::class.java
        )
    }
}
