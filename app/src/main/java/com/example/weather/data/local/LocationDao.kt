package com.example.weather.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.weather.data.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDao {

    @Transaction
    open suspend fun select(location: Location) {
        val selectedLocation = getLocation(location.id)
        selectedLocation?.let { it ->
            if (it.isAutoLocation) {
                val similarLocation = getLocationByCoordinates(it.latitude, it.longitude, false)
                similarLocation?.let { it1 -> delete(it1) }
            }
            if (!it.isSelected) {
                it.isSelected = true
                update(it)
                unselectLocations(it.id, false)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(location: Location)

    @Delete
    abstract suspend fun delete(location: Location)

    @Update
    abstract suspend fun update(location: Location)

    @Query("SELECT * FROM LIST_LOCATIONS ORDER BY id DESC")
    abstract fun getListLocations(): Flow<List<Location>>

    @Query("SELECT *  FROM LIST_LOCATIONS WHERE id=:id ")
    abstract suspend fun getLocation(id: Int): Location?

    @Query("SELECT * FROM LIST_LOCATIONS ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLastLocation(): Location?

    @Query("SELECT * FROM  LIST_LOCATIONS WHERE isAutoLocation = :isAutoLocation")
    abstract suspend fun getAutoLocation(isAutoLocation: Boolean): Location?

    @Query("SELECT * FROM LIST_LOCATIONS WHERE isSelected = :isSelected")
    abstract suspend fun getSelectedLocation(isSelected: Boolean): Location?

    @Query(
        "SELECT * FROM LIST_LOCATIONS WHERE latitude = :latitude AND longitude = :longitude AND isAutoLocation = :isAutoLocation" // ktlint-disable max-line-length
    )
    abstract suspend fun getLocationByCoordinates(
        latitude: Double,
        longitude: Double,
        isAutoLocation: Boolean
    ): Location?

    @Query("UPDATE LIST_LOCATIONS SET isSelected = :isSelected  WHERE id != :id")
    abstract suspend fun unselectLocations(id: Int, isSelected: Boolean)
}
