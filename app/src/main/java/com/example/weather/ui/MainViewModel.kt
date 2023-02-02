package com.example.weather.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.Location
import com.example.weather.data.model.WeatherResult
import com.example.weather.data.repository.GeocodingRepository
import com.example.weather.data.repository.LocationRepository
import com.example.weather.data.repository.SwitchPreferencesRepository
import com.example.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE, NONE }

data class Event(
    val isAddLocationPressed: Boolean = false,
    val hasLocationServiceError: Boolean = false,
    val isEnableProvidersClicked: Boolean = false,
    val hasErrorCoordinates: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val switchPreferencesRepository: SwitchPreferencesRepository,
    private val geoCodingRepository: GeocodingRepository
) : ViewModel() {

    private val _event = MutableStateFlow(Event())
    val event: StateFlow<Event> = _event.asStateFlow()

    private val _weatherApiStatus: MutableStateFlow<ApiStatus?> = MutableStateFlow(null)
    val weatherApiStatus = _weatherApiStatus.asStateFlow()

    private val _weather: MutableStateFlow<WeatherResult?> = MutableStateFlow(null)
    val weather = _weather.asStateFlow()

    private val _geocodingApiStatus: MutableStateFlow<ApiStatus?> = MutableStateFlow(null)
    val geocodingApiStatus = _geocodingApiStatus.asStateFlow()

    private val _locationGeocoder: MutableStateFlow<Location?> = MutableStateFlow(null)
    val locationGeocoder = _locationGeocoder.asStateFlow()

    val listLocationsFlow = locationRepository.listLocationsFlow

    private val _toolbarTitle: MutableStateFlow<String?> = MutableStateFlow(null)
    val toolbarTitle = _toolbarTitle.asStateFlow()

    val isChecked = switchPreferencesRepository.isChecked

    fun onIsCheckedChange(isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        switchPreferencesRepository.onCheckedChange(isChecked)
    }

    fun getAutoLocation(callback: (Location?) -> Unit) {
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
            _geocodingApiStatus.value = ApiStatus.LOADING
            try {
                val geoCoding = geoCodingRepository.getGeoCoding(
                    lat = latitude,
                    lon = longitude
                )
                val locality =
                    "${geoCoding.address.city.substringBefore(" ")}, ${geoCoding.address.country_code.uppercase()}" // ktlint-disable max-line-length
                setLocationGeocoder(Location(locality, latitude, longitude, isAutoLocation = true))
                addLocation(Location(locality, latitude, longitude, isAutoLocation = true))
                _geocodingApiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _geocodingApiStatus.value = ApiStatus.ERROR
            }
        }
    }

    private fun setLocationGeocoder(location: Location) {
        _locationGeocoder.value = location
    }

    fun updateWeatherApiStatus(weatherApiStatus: ApiStatus?) {
        _weatherApiStatus.value = weatherApiStatus
    }

    fun setToolbarTitle(toolbarTitle: String) {
        _toolbarTitle.value = toolbarTitle
    }

    fun addLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            val autoLocation = locationRepository.getAutoLocation(true)
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
                    setToolbarTitle(it.locality.substringBefore(','))
                }
            }
            locationRepository.select(location)
        }
    }

    fun getCoordinatesFromString(context: Context, string: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context)
                val listAddresses: List<Address>? =
                    geocoder.getFromLocationName(string.toString(), 5)
                listAddresses?.first()?.let { address ->
                    val locality =
                        "${string?.replaceFirstChar { it.uppercase() }}, ${address.countryCode}"
                    val latitude = address.latitude
                    val longitude = address.longitude
                    addLocation(
                        Location(locality, latitude, longitude)
                    )
                }
            } catch (e: Exception) {
                updateEvent(Event(hasErrorCoordinates = true))
            }
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            _event.value = event
        }
    }

    fun delete(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.delete(location)
            if (location.isSelected) {
                val lastLocation = getLastLocation()
                lastLocation?.let { selectLocation(it) }
            }
        }
    }

    private suspend fun getLastLocation() = locationRepository.getLastLocation()

    private suspend fun insert(location: Location) = locationRepository.insert(location)

    suspend fun getSelectedLocation() = locationRepository.getSelectedLocation()

    private suspend fun getLocation(id: Int) = locationRepository.getLocation(id)
}
