package com.example.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.database.PlaceDoe
import com.example.weather.database.PlaceItem

class PlaceRepository(private val placeDoe: PlaceDoe) {

    val allPlaceItems = placeDoe.getAllPlaceItems()

    fun getLastPlace() = placeDoe.getLastPlace()

    fun getCheckedItem(isChecked: Boolean) = placeDoe.getCheckedItem(isChecked)

    suspend fun uncheckAllExcept(placeId: Long, isChecked: Boolean) = placeDoe.uncheckAllExcept(placeId, isChecked)

    suspend fun getPlaceItem(id: Long) = placeDoe.getPlaceItem(id)

    suspend fun exists(locality: String) = placeDoe.exists(locality)

    suspend fun getAutoLocation(isAutoLocation: Boolean) = placeDoe.getAutoLocationItem(isAutoLocation)



    suspend fun insert(placeItem: PlaceItem) = placeDoe.insert(placeItem)

    suspend fun update(placeItem: PlaceItem) = placeDoe.update(placeItem)

    suspend fun delete(placeItem: PlaceItem) = placeDoe.delete(placeItem)

}