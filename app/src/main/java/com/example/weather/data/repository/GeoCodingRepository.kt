package com.example.weather.data.repository

import com.example.weather.network.*
import retrofit2.http.Query
import javax.inject.Inject

class GeoCodingRepository @Inject constructor() {

    suspend fun getGeoCoding(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): List<GeoCodingItem> {
        return GeoCodingApi.GEO_CODING_RETROFIT_SERVICE.getGeoCodingApi(
            lat =  lat,
            lon =  lon
        )
    }
}