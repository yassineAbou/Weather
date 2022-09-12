package com.example.weather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.Location
import com.example.weather.data.repository.GeoCodingRepository
import com.example.weather.data.repository.LocationRepository
import com.example.weather.data.repository.SwitchPreferencesRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.network.ConnectionState
import com.example.weather.network.NetworkStatusTracker
import com.example.weather.network.WeatherResult
import com.example.weather.network.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


enum class ApiStatus { LOADING, ERROR, DONE, IDLE }

private const val TAG = "MainViewModel"


data class ListLocationsEvent(
    val isAddLocationPressed: Boolean = false,
    val hasLocationServiceError: Boolean = false,
    val isEnableProvidersClicked: Boolean = false,
    val hasErrorCoordinates: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val networkStatusTracker: NetworkStatusTracker,
    private val switchPreferencesRepository: SwitchPreferencesRepository,
    private val geoCodingRepository: GeoCodingRepository
) : ViewModel() {



    private val _listLocationsEvent = MutableStateFlow(ListLocationsEvent())
    val listLocationsEvent: StateFlow<ListLocationsEvent> = _listLocationsEvent.asStateFlow()


    private val _weatherApiStatus: MutableStateFlow<ApiStatus?> = MutableStateFlow(null)
    val weatherApiStatus = _weatherApiStatus.asStateFlow()

    private val _weather: MutableStateFlow<WeatherResult?> = MutableStateFlow(null)
    val weather = _weather.asStateFlow()

    private val _geoCodingApiStatus: MutableStateFlow<ApiStatus?> = MutableStateFlow(null)
    val geoCodingApiStatus = _geoCodingApiStatus.asStateFlow()

    private val _locationGeoCoder:  MutableStateFlow<Location?> = MutableStateFlow(null)
    val locationGeoCoder = _locationGeoCoder.asStateFlow()

    val listLocationsFlow = locationRepository.listLocationsFlow

    private val _locationName: MutableStateFlow<String?> = MutableStateFlow(null)
    val locationName = _locationName.asStateFlow()

    val connectionState =
        networkStatusTracker.networkStatus
            .map(
                onUnavailable = { ConnectionState.Error },
                onAvailable = { ConnectionState.Fetched },
            )

    val isChecked = switchPreferencesRepository.isChecked

    fun onIsCheckedChange(isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        switchPreferencesRepository.onCheckedChange(isChecked)
    }

    fun getAutoLocation(callback: (Location?) -> Unit)   {
        viewModelScope.launch(Dispatchers.IO) {
            callback(locationRepository.getAutoLocation(true))
        }
    }

     fun getWeather(latitude: Double, longitude: Double) {

        viewModelScope.launch(Dispatchers.IO) {
                _weatherApiStatus.value = ApiStatus.LOADING
                try {
                    _weather.value = weatherRepository.getWeather(
                            lat = latitude,
                            lon = longitude

                    )
                    _weatherApiStatus.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _weatherApiStatus.value = ApiStatus.ERROR
                }
        }

    }


    fun getGeoCoding(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _geoCodingApiStatus.value = ApiStatus.LOADING
            try {
                val listGeoCoding  = geoCodingRepository.getGeoCoding(
                    lat = latitude,
                    lon = longitude
                )
                val locality = "${listGeoCoding.first().local_names.en}, ${listGeoCoding.first().country}"
                saveLocationGeoCoder(Location(locality, latitude, longitude, isAutoLocation = true))
                addLocation(Location(locality, latitude, longitude, isAutoLocation = true))
                _geoCodingApiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _geoCodingApiStatus.value = ApiStatus.ERROR
            }
        }

    }


    fun saveLocationGeoCoder(location: Location) {
        _locationGeoCoder.value = location
    }

    fun changeWeatherApiStatus(weatherApiStatus: ApiStatus?) {
        _weatherApiStatus.value = weatherApiStatus
    }

    fun setLocationName(locationName: String) {
        _locationName.value = locationName
    }


     fun addLocation(location: Location) {
         viewModelScope.launch(Dispatchers.IO) {
             val autoLocation = getLocationByLocality(location.locality, true)
             if (location.locality != autoLocation?.locality) {
                 insert(location)
                 val lastLocation = getLastLocation()
                 lastLocation?.let { selectLocation(it) }
             }
         }
     }

    fun selectLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            getLocation(location.id)?.let {
                if (!it.isSelected) {
                    getWeather(it.latitude, it.longitude)
                    setLocationName(it.locality.substringBefore(','))
                }
            }
            locationRepository.selectLocation(location)
        }
    }

    fun getCoordinatesByLocation(context: Context, location: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context)
                val addresses: List<Address> =
                    geocoder.getFromLocationName(location, 5)
                val address = addresses.first()

                val locality = "${location?.replaceFirstChar { it.uppercase() }}, ${address.countryCode}"
                val latitude = address.latitude
                val longitude = address.longitude
                Log.e(TAG, "latitude = $latitude longitude = $longitude", )
                addLocation(
                    Location(locality, latitude, longitude)
                )

            } catch (e: Exception) {
                changeListLocationsEvent(ListLocationsEvent(hasErrorCoordinates = true))
            }
        }
    }


     fun changeListLocationsEvent(listLocationsEvent: ListLocationsEvent) {
         viewModelScope.launch {
             _listLocationsEvent.value = listLocationsEvent
         }
    }


    fun deleteLocation(location: Location) {
       viewModelScope.launch(Dispatchers.IO) {
           delete(location)
           if (location.isSelected) {
               val lastLocation = getLastLocation()
               lastLocation?.let { selectLocation(it) }
           }
       }
    }

    private suspend fun delete(location: Location) = locationRepository.delete(location)

    private suspend fun getLastLocation() = locationRepository.getLastLocation()

    private suspend fun getLocationByLocality(locality: String, isSelected: Boolean) = locationRepository.getLocationByLocality(locality, isSelected)

    private suspend fun insert(location: Location)  = locationRepository.insert(location)

    suspend fun getSelectedLocation() = locationRepository.getSelectedLocation()

    private suspend fun getLocation(id: Int) = locationRepository.getLocation(id)



}

