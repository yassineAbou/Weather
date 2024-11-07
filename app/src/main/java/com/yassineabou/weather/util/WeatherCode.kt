package com.yassineabou.weather.util

import com.yassineabou.weather.R


fun getWeatherDetails(weatherCode: Int): Pair<Int, String> {
    return when (weatherCode) {
           0 -> Pair(R.drawable.d01, "Sunny")
           1 -> Pair(R.drawable.d01, "Mainly sunny")
           2 -> Pair(R.drawable.d02, "Partly Cloudy")
           3 -> Pair(R.drawable.dn03, "Cloudy")
           45 -> Pair(R.drawable.dn50, "Foggy")
           48 -> Pair(R.drawable.dn50, "Rime fog")
           51 -> Pair(R.drawable.dn09, "Light Drizzle")
           53 -> Pair(R.drawable.dn09, "Drizzle")
           55 -> Pair(R.drawable.dn09, "Heavy Drizzle")
           56 -> Pair(R.drawable.dn09, "Light Freezing Drizzle")
           57 -> Pair(R.drawable.dn09, "Freezing Drizzle")
           61 -> Pair(R.drawable.d10, "Light Rain")
           63 -> Pair(R.drawable.d10, "Rain")
           65 -> Pair(R.drawable.d10, "Heavy Rain")
           66 -> Pair(R.drawable.d10, "Light Freezing Rain")
           67 -> Pair(R.drawable.d10, "Freezing Rain")
           71 -> Pair(R.drawable.dn13, "Light Snow")
           73 -> Pair(R.drawable.dn13, "Snow")
           75 -> Pair(R.drawable.dn13, "Heavy Snow")
           77 -> Pair(R.drawable.dn13, "Snow Grains")
           80 -> Pair(R.drawable.dn09, "Light Showers")
           81 -> Pair(R.drawable.dn09, "Showers")
           82 -> Pair(R.drawable.dn09, "Heavy Showers")
           85 -> Pair(R.drawable.dn13, "Light Snow Showers")
           86 -> Pair(R.drawable.dn13, "Snow Showers")
           95 -> Pair(R.drawable.dn11, "Thunderstorm")
           96 -> Pair(R.drawable.dn11, "Light Thunderstorms With Hail")
           99 -> Pair(R.drawable.dn11, "Thunderstorms With Hail")
           else -> Pair(100, "Unknown")
    }
}