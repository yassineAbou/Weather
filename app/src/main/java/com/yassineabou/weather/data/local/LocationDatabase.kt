package com.yassineabou.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yassineabou.weather.data.model.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao
}
