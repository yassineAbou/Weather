package com.example.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.R
import java.util.*
import kotlin.properties.Delegates

@Entity(tableName = "place_Item_table")
data class PlaceItem(

    var locality: String,
    val lat: String,
    val lon: String,
   // val currentWeather: String = "",
    val isAutoLocation: Boolean = false,
    var isChecked: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {

}
