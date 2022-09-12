package com.example.weather.data.local

import androidx.room.*
import com.example.weather.data.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
abstract class  LocationDao {


    @Transaction
    open suspend fun selectLocation(location: Location) {
        val selectedLocation = getLocation(location.id)
        selectedLocation?.let { it ->
            if (it.isAutoLocation) {
                val sameLocation = getLocationByLocality(it.locality, false)
                sameLocation?.let { it1 -> delete(it1) }
            }
            if (!it.isSelected) {
                it.isSelected = true
                update(it)
                unselectLocations(it.id, false)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(location: Location)

    @Delete
    abstract suspend fun  delete(location: Location)

    @Update
    abstract suspend fun  update(location: Location)

    @Query("SELECT * FROM LIST_LOCATIONS ORDER BY id DESC")
    abstract fun getListLocations(): Flow<List<Location>>

    @Query("SELECT *  FROM LIST_LOCATIONS WHERE id=:id ")
    abstract suspend fun getLocation(id: Int): Location?

    @Query("SELECT * FROM LIST_LOCATIONS ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLastLocation(): Location?

    @Query("SELECT * FROM  LIST_LOCATIONS WHERE isAutoLocation = :isAutoLocation")
    abstract suspend fun  getAutoLocation(isAutoLocation: Boolean): Location?

    @Query("SELECT * FROM LIST_LOCATIONS WHERE isSelected = :isSelected")
    abstract suspend fun  getSelectedLocation(isSelected: Boolean):Location?

    @Query("SELECT * FROM LIST_LOCATIONS WHERE locality = :locality AND isAutoLocation = :isAutoLocation")
    abstract suspend fun  getLocationByLocality(locality: String, isAutoLocation:Boolean): Location?

    @Query("UPDATE LIST_LOCATIONS SET isSelected = :isSelected  WHERE id != :id")
    abstract suspend fun unselectLocations(id: Int, isSelected: Boolean)
}