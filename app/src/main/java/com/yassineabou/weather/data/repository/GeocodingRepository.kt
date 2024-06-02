package com.yassineabou.weather.data.repository

import com.yassineabou.weather.data.model.Geocoding
import com.yassineabou.weather.data.remote.GeocodingApi
import javax.inject.Inject

class GeocodingRepository @Inject constructor() {

    suspend fun getGeoCoding(
        lat: Double,
        lon: Double
    ): Geocoding {
        return GeocodingApi.GEOCODING_RETROFIT_SERVICE.getGeocodingApi(
            lat = lat,
            lon = lon
        )
    }
}
