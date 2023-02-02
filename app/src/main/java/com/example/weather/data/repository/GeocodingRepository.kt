package com.example.weather.data.repository

import com.example.weather.data.model.Geocoding
import com.example.weather.data.remote.GeocodingApi
import javax.inject.Inject
import retrofit2.http.Query

class GeocodingRepository @Inject constructor() {

    suspend fun getGeoCoding(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Geocoding {
        return GeocodingApi.GEO_CODING_RETROFIT_SERVICE.getGeocodingApi(
            lat = lat,
            lon = lon
        )
    }
}
