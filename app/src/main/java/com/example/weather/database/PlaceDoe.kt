package com.example.weather.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.weather.network.WeatherResult

@Dao
interface PlaceDoe {

    @Insert
    suspend fun insert(placeItem: PlaceItem)

    @Delete
    suspend fun delete(placeItem: PlaceItem)

    @Update
    suspend fun update(placeItem: PlaceItem)

    @Query("SELECT * FROM PLACE_ITEM_TABLE ORDER BY id DESC")
    fun getAllPlaceItems(): LiveData<List<PlaceItem>>

    @Query("SELECT * FROM PLACE_ITEM_TABLE WHERE id=:id ")
    suspend fun getPlaceItem(id: Long): PlaceItem?

    @Query("SELECT * FROM PLACE_ITEM_TABLE")
     suspend fun getPlaces(): List<PlaceItem>

    @Query("SELECT * FROM PLACE_ITEM_TABLE ORDER BY id DESC LIMIT 1")
    fun getLastPlace(): LiveData<PlaceItem?>

    @Query("SELECT EXISTS (SELECT 1 FROM PLACE_ITEM_TABLE WHERE locality = :locality)")
    suspend fun exists(locality: String): Boolean

    @Query("SELECT * FROM  PLACE_ITEM_TABLE WHERE isAutoLocation = :isAutoLocation")
    suspend fun getAutoLocationItem(isAutoLocation: Boolean): PlaceItem?
}