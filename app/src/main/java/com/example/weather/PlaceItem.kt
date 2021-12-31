package com.example.weather

import java.util.*

data class PlaceItem(
    var locality: String,
    var time: String,
    var isAutoLocation: Boolean = false,
    val id: UUID = UUID.randomUUID()
)
