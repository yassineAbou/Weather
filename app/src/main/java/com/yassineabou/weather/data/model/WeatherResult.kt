package com.yassineabou.weather.data.model

import com.squareup.moshi.Json

data class WeatherResult(
    val latitude: Double,
    val longitude: Double,
    @Json(name = "generationtime_ms")
    val generationtimeMs: Double,
    @Json(name = "utc_offset_seconds")
    val utcOffsetSeconds: Long,
    val timezone: String,
    @Json(name = "timezone_abbreviation")
    val timezoneAbbreviation: String,
    val elevation: Double,
    @Json(name = "current_units")
    val currentUnits: CurrentUnits,
    val current: Current,
    @Json(name = "hourly_units")
    val hourlyUnits: HourlyUnits,
    val hourly: Hourly,
    @Json(name = "daily_units")
    val dailyUnits: DailyUnits,
    val daily: Daily,
)

data class CurrentUnits(
    val time: String,
    val interval: String,
    @Json(name = "temperature_2m")
    val temperature2m: String,
    @Json(name = "relative_humidity_2m")
    val relativeHumidity2m: String,
    @Json(name = "is_day")
    val isDay: String,
    @Json(name = "weather_code")
    val weatherCode: String,
    @Json(name = "wind_speed_10m")
    val windSpeed10m: String,
)

data class Current(
    val time: String,
    val interval: Long,
    @Json(name = "temperature_2m")
    val temperature2m: Double,
    @Json(name = "relative_humidity_2m")
    val relativeHumidity2m: Long,
    @Json(name = "is_day")
    val isDay: Long,
    @Json(name = "weather_code")
    val weatherCode: Long,
    @Json(name = "wind_speed_10m")
    val windSpeed10m: Double,
)

data class HourlyUnits(
    val time: String,
    @Json(name = "temperature_2m")
    val temperature2m: String,
    @Json(name = "weather_code")
    val weatherCode: String,
)

data class Hourly(
    val time: List<String>,
    @Json(name = "temperature_2m")
    val temperature2m: List<Double>,
    @Json(name = "weather_code")
    val weatherCode: List<Long>,
)

data class DailyUnits(
    val time: String,
    @Json(name = "weather_code")
    val weatherCode: String,
    @Json(name = "temperature_2m_max")
    val temperature2mMax: String,
    @Json(name = "temperature_2m_min")
    val temperature2mMin: String,
)

data class Daily(
    val time: List<String>,
    @Json(name = "weather_code")
    val weatherCode: List<Long>,
    @Json(name = "temperature_2m_max")
    val temperature2mMax: List<Double>,
    @Json(name = "temperature_2m_min")
    val temperature2mMin: List<Double>,
)


