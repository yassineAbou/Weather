package com.example.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaceItem::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {

    abstract val placeDoe: PlaceDoe

    companion object {

        @Volatile
        private var INSTANCE: PlaceDatabase? = null
        fun getInstance(context: Context): PlaceDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlaceDatabase::class.java,
                        "place_items_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}