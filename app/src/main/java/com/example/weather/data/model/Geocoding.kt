package com.example.weather.data.model

data class Geocoding(
    val address: Address,
    val boundingbox: List<String>,
    val display_name: String,
    val lat: String,
    val licence: String,
    val lon: String,
    val osm_id: Int,
    val osm_type: String,
    val place_id: Int,
    val powered_by: String
)

data class Address(
    val city: String,
    val country: String,
    val country_code: String,
    val county: String,
    val postcode: String,
    val region: String,
    val state_district: String
)
