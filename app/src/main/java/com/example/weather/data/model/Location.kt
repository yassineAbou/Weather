package com.example.weather.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "list_locations",
    indices = [Index(value = ["locality", "isAutoLocation"], unique = true)]
) data class Location(

    var locality: String,
    val latitude: Double,
    val longitude: Double,
    val isAutoLocation: Boolean = false,
    var isSelected: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
