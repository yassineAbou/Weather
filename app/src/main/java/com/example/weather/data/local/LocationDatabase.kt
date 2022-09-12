package com.example.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.data.local.LocationDao
import com.example.weather.data.model.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao

}