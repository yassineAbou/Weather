package com.example.weather.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.weather.network.WeatherResult
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDoe {

    @Insert
    suspend fun insert(placeItem: PlaceItem)

    @Delete
    suspend fun delete(placeItem: PlaceItem)

    @Update
    suspend fun update(placeItem: PlaceItem)

    @Query("SELECT * FROM PLACE_ITEM_TABLE ORDER BY id DESC")
    fun getAllPlaceItems(): Flow<List<PlaceItem>>

    @Query("SELECT * FROM PLACE_ITEM_TABLE WHERE id=:id ")
    suspend fun getPlaceItem(id: Long): PlaceItem?

    @Query("SELECT * FROM PLACE_ITEM_TABLE ORDER BY id DESC LIMIT 1")
    fun getLastPlace(): Flow<PlaceItem?>

    @Query("SELECT EXISTS (SELECT * FROM PLACE_ITEM_TABLE WHERE locality = :locality)")
    suspend fun exists(locality: String): Boolean

    @Query("SELECT * FROM  PLACE_ITEM_TABLE WHERE isAutoLocation = :isAutoLocation")
    suspend fun getAutoLocationItem(isAutoLocation: Boolean): PlaceItem?

    @Query("SELECT * FROM PLACE_ITEM_TABLE WHERE isChecked = :isChecked")
    fun getCheckedItem(isChecked: Boolean): Flow<PlaceItem?>

    @Query("UPDATE PLACE_ITEM_TABLE   SET isChecked = :isChecked  WHERE id != :placeId")
    suspend fun uncheckAllExcept(placeId: Long, isChecked: Boolean)
}