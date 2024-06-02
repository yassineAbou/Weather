package com.yassineabou.weather.data.model

import com.squareup.moshi.Json

data class Geocoding(
    @Json(name = "place_id") val placeId: Long,
    val licence: String,
    @Json(name = "osm_type") val osmType: String,
    @Json(name = "osm_id") val osmId: Long,
    val lat: String,
    val lon: String,
    @Json(name = "display_name") val displayName: String,
    val address: Address,
    val boundingbox: List<String>,
)

data class Address(
    val city: String,
    val county: String,
    @Json(name = "state_district") val stateDistrict: String,
    @Json(name = "ISO3166-2-lvl5") val iso31662Lvl5: String,
    val region: String,
    @Json(name = "ISO3166-2-lvl4") val iso31662Lvl4: String,
    val postcode: String,
    val country: String,
    @Json(name = "country_code") val countryCode: String,
)
