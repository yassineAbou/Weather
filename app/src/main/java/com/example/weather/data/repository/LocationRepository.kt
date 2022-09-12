package com.example.weather.data.repository


import com.example.weather.data.model.Location
import com.example.weather.data.local.LocationDao
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
    ) {

    val listLocationsFlow = locationDao.getListLocations().distinctUntilChanged()

    suspend fun getSelectedLocation() = locationDao.getSelectedLocation(true)

    suspend fun delete(location: Location) = locationDao.delete(location)

    suspend fun insert(location: Location) = locationDao.insert(location)

    suspend fun getLastLocation() = locationDao.getLastLocation()

    suspend fun getLocationByLocality(locality: String, isSelected: Boolean) = locationDao.getLocationByLocality(locality, isSelected)

    suspend fun selectLocation(location: Location) = locationDao.selectLocation(location)

    suspend fun getAutoLocation(isAutoLocation: Boolean) = locationDao.getAutoLocation(isAutoLocation)

    suspend fun getLocation(id: Int) = locationDao.getLocation(id)


}