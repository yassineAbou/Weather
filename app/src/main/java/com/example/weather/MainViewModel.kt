package com.example.weather

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.database.PlaceItem
import com.example.weather.database.PreferencesManager
import com.example.weather.network.WeatherResult
import com.example.weather.repository.PlaceRepository

import com.example.weather.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


// TODO: auto location
// TODO: update
// TODO: permissions
// TODO: if there is something else to do

enum class WeatherApiStatus { LOADING, ERROR, DONE }

private const val TAG = "MainViewModel"


data class UiState(
    val GetLastItem: Boolean = false,
    val NavigateToNewLocationDialog: Boolean = false,
    val ShowAlertDialog: Boolean = false,
    val GoToLocationSettings: Boolean = false,
    val ShowErrorDialog: Boolean = false,
)

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placeRepository: PlaceRepository,
    private val preferencesManager: PreferencesManager,
    private val application: Application
) : ViewModel() {



    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val selected = preferencesManager.selected

    fun readSelected( callback: (Boolean) -> Unit)   {
        viewModelScope.launch(Dispatchers.IO) {
             callback(preferencesManager.readSelected())

        }
    }

    fun onSelectAutoLocation(selected: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferencesManager.onSelectAutoLocation(selected)
    }

    private val _status: MutableStateFlow<WeatherApiStatus?> = MutableStateFlow(null)
    val status = _status.asStateFlow()

    private val _weather: MutableStateFlow<WeatherResult?> = MutableStateFlow(null)
    val weather = _weather.asStateFlow()

    fun getWeather(lat: String, lon: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _status.value = WeatherApiStatus.LOADING
                try {
                    _weather.value = weatherRepository.getWeather(
                            lat = lat,
                            lon = lon

                    )
                    _status.value = WeatherApiStatus.DONE
                    Log.e(TAG, "getWeather: ${weather.value?.current?.wind_deg}")
                } catch (e: Exception) {
                    _status.value = WeatherApiStatus.ERROR
                    Log.e(TAG, "$e")
                }
            }
        }

    }

    fun onErrorStatus() {
        _status.value = WeatherApiStatus.ERROR
    }

    private val _currentLocation: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun setLocation(location: String) {
        _currentLocation.value = location
    }

    val placeItems = placeRepository.allPlaceItems

    fun addPlaceItem(placeItem: PlaceItem) {
        viewModelScope.launch {
            with(Dispatchers.IO) {
                val isExist = async { exists(placeItem.locality) }
                if (!isExist.await()) {
                    insert(placeItem)
                    //onUpdated()
                    onChangeUiState(UiState(GetLastItem = true))
                }
            }
        }
    }

    fun checkNewItemAdded(item: PlaceItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItemAdded =  getPlaceItem(item.id)
            newItemAdded?.let {
                listOf(
                    async {
                        if (!it.isChecked) {
                            it.isChecked = true
                            update(it)
                        }
                    },
                    async {
                        uncheckAllExcept(it.id, false)
                    }
                ).awaitAll()
            }
        }
    }

    fun getCityLatitude(context: Context, city: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gc = Geocoder(context)
                    val addresses: List<Address> =
                        gc.getFromLocationName(city, 5)
                   // if (addresses.isNotEmpty()) {
                        val a = addresses.first()

                        val locality = "${a.locality}, ${a.countryCode}"
                        val lat = a.latitude.toString()
                        val lon = a.longitude.toString()
                        val newPlace = PlaceItem(locality, lat, lon)
                        addPlaceItem(newPlace)
                 //   }

            } catch (e: Exception) {
                onChangeUiState(UiState(ShowErrorDialog = true))
            }
        }
    }


     fun onChangeUiState(state: UiState) {
         viewModelScope.launch {
             _uiState.value = state
         }
    }


    fun removePlaceItem(placeItem: PlaceItem) {
        if (!placeItem.isAutoLocation)
            viewModelScope.launch {
                delete(placeItem)
                if (placeItem.isChecked)
                    //onUpdated()
                    onChangeUiState(UiState(GetLastItem = true))

            }
    }

    private val _autoLocation: MutableStateFlow<PlaceItem?> = MutableStateFlow(null)
    val autoLocation = _autoLocation.asStateFlow()

    fun setAutoLocation(placeItem: PlaceItem?) {
        _autoLocation.value = placeItem
    }

    fun addAutoPlaceItem(placeItem: PlaceItem) {
        val isAutoLocation = placeItem.isAutoLocation
        if (isAutoLocation) {
            addPlaceItem(placeItem)
        }
    }

    fun removeAutoPlaceItem() {
        viewModelScope.launch {
            with(Dispatchers.IO) {
                val autoLocation = async { getAutoLocation(true) }
                autoLocation.await()?.let {
                    delete(it)
                    if (it.isChecked)
                        //onUpdated()
                        onChangeUiState(UiState(GetLastItem = true))

                }
            }
        }
    }

    //-------
    // Data
    //-------

    fun getLastPlace() = placeRepository.getLastPlace()

    suspend fun uncheckAllExcept(placeId: Long, isChecked: Boolean) = placeRepository.uncheckAllExcept(placeId, isChecked)

    suspend fun insert(placeItem: PlaceItem) = placeRepository.insert(placeItem)

    suspend fun getPlaceItem(id: Long) = placeRepository.getPlaceItem(id)

    suspend fun exists(locality: String) = placeRepository.exists(locality)

    suspend fun update(placeItem: PlaceItem) = placeRepository.update(placeItem)

    suspend fun getAutoLocation(isAutoLocation: Boolean) = placeRepository.getAutoLocation(isAutoLocation)

    fun getCheckedItem(isChecked: Boolean) = placeRepository.getCheckedItem(isChecked)

    suspend fun delete(placeItem: PlaceItem) = placeRepository.delete(placeItem)


    //------------------
    // ViewModelFactory
    //------------------

    class Factory(
        private val weatherRepository: WeatherRepository,
        private val placeRepository: PlaceRepository,
        private val preferencesManager: PreferencesManager,
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(weatherRepository, placeRepository, preferencesManager, application) as T
        }
    }
}

