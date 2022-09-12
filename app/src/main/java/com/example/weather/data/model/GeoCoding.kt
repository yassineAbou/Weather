package com.example.weather.network

data class GeoCodingItem(
    val country: String,
    val lat: Double,
    val local_names: GeoCodingNames,
    val lon: Double,
    val name: String
)

data class GeoCodingNames(
    val ar: String,
    val ascii: String,
    val en: String,
    val es: String,
    val et: String,
    val feature_name: String,
    val fr: String,
    val ko: String,
    val ml: String,
    val ru: String,
    val uk: String
)


