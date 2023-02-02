package com.example.weather.data.repository

import com.example.weather.data.local.LocationDao
import com.example.weather.data.model.Location
import javax.inject.Inject
import kotlinx.coroutines.flow.distinctUntilChanged

class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
) {

    val listLocationsFlow = locationDao.getListLocations().distinctUntilChanged()

    suspend fun getSelectedLocation() = locationDao.getSelectedLocation(true)

    suspend fun delete(location: Location) = locationDao.delete(location)

    suspend fun insert(location: Location) = locationDao.insert(location)

    suspend fun getLastLocation() = locationDao.getLastLocation()

    suspend fun select(location: Location) = locationDao.select(location)

    suspend fun getAutoLocation(isAutoLocation: Boolean) =
        locationDao.getAutoLocation(isAutoLocation)

    suspend fun getLocation(id: Int) = locationDao.getLocation(id)
}
