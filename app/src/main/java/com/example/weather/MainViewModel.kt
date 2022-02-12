package com.example.weather

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.database.PlaceItem
import com.example.weather.network.WeatherResult
import com.example.weather.repository.PlaceRepository
import com.example.weather.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*


enum class WeatherApiStatus { LOADING, ERROR, DONE }

private const val TAG = "MainViewModel"

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placeRepository: PlaceRepository,
    private val application: Application
) : ViewModel() {


    private val _status = MutableLiveData<WeatherApiStatus>()

    val status: MutableLiveData<WeatherApiStatus>
        get() = _status


    private val _weather = MutableLiveData<WeatherResult?>()

    val weather: MutableLiveData<WeatherResult?>
        get() = _weather

    suspend fun getWeather(lat: String, lon: String) {
        _status.value = WeatherApiStatus.LOADING
        try {
            _weather.value = weatherRepository.getWeather(
                lat = lat,
                lon = lon
            )
            _status.value = WeatherApiStatus.DONE
        } catch (e: Exception) {
            _status.value = WeatherApiStatus.ERROR
            Log.e(TAG, "$e",)
        }
    }


    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: MutableLiveData<Boolean>
        get() = _isAdded

    fun onUpdated() {
        _isAdded.value = true
    }

    fun onUpdatedComplete() {
        _isAdded.value = false
    }

    val placeItems = placeRepository.allPlaceItems


    fun addPlaceItem(placeItem: PlaceItem) {
        viewModelScope.launch {
            with(Dispatchers.IO) {
                val isExist = async { exists(placeItem.locality) }
                if (!isExist.await()) {
                    insert(placeItem)
                    onUpdated()
                }
            }
        }
    }

    fun updateIsChecked(item: PlaceItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val child1 = async { getPlaceItem(item.id) }
                val placeItem = child1.await()

                placeItem?.let {
                    if (!it.isChecked) {
                        it.isChecked = true
                        val child2 = launch { update(it) }
                        child2.join()
                        Log.e(TAG, "${placeItem.locality}")
                    }
                }


                val child3 = async { getPlaces() }
                val places = child3.await()

                val runningTasks = places.map { place ->
                    async {
                        val singlePlaceItem = getPlaceItem(place.id)
                        place to singlePlaceItem
                    }
                }

                val responses = runningTasks.awaitAll()

                responses.forEach { (place, response) ->
                    if (response != placeItem) {
                        places.find { it.id == place.id }?.isChecked = false
                        update(place)
                    }
                }

                }
            }
      }



     fun getCityLatitude(context: Context, city: String?) {

               val geocoder = Geocoder(context, context.resources.configuration.locale)
               var addresses: List<Address>? = null
               var latLng: LatLng? = null
               try {
                   addresses = geocoder.getFromLocationName(city, 1)
                   val address = addresses[0]
                   latLng = LatLng(address.latitude, address.longitude)
                   val currentLocation = geocoder.getFromLocation(
                       latLng.latitude,
                       latLng.longitude,
                       1
                   )

                   val locality = "${currentLocation.first().locality}, ${currentLocation.first().countryCode}"
                   val lat = latLng.latitude.toString()
                   val lon = latLng.longitude.toString()
                   val newPlace = PlaceItem(locality, lat, lon, isChecked = true)
                   addPlaceItem(newPlace)

               } catch (e: Exception) {
                   showErrorDialog(context)
               }

    }

    private val _navigateToNewLocationDialog = MutableLiveData<Boolean> ()
    val navigateToNewLocationDialog: MutableLiveData<Boolean>
        get() = _navigateToNewLocationDialog

    fun onNavigation() {
        _navigateToNewLocationDialog.value = true
    }

     fun onNavigationComplete() {
        _navigateToNewLocationDialog.value = false
    }

    fun showErrorDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle("Error")
            setMessage("This city doesn't exist")
            setCancelable(false)
            setPositiveButton("TRY AGAIN") { _, _ ->
                   onNavigation()
            }
            setNegativeButton("CLOSE") { _, _ ->   }

        }.create().show()
    }




    fun removePlaceItem(placeItem: PlaceItem) {
        if (!placeItem.isAutoLocation)
            viewModelScope.launch {
                delete(placeItem)
                if (placeItem.isChecked)
                onUpdated()
            }
    }

    private val _isEnabled = MutableLiveData<Boolean> ()
    val isEnabled: MutableLiveData<Boolean>
        get() = _isEnabled

    fun onInvalid() {
        _isEnabled.value = true
    }

    private fun onInvalidComplete() {
        _isEnabled.value = false
    }


    private val _goToLocationSettings = MutableLiveData<Boolean> ()
    val goToLocationSettings: MutableLiveData<Boolean>
        get() = _goToLocationSettings

    private fun onGoToLocationSetting() {
        _goToLocationSettings.value = true
    }

    fun onGoComplete() {
        _isEnabled.value = false
    }


    fun addAutoPlaceItem(placeItem: PlaceItem, context: Context) {
        val isAutoLocation = placeItem.isAutoLocation
        if (isAutoLocation and (_isEnabled.value != true)) {
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
                    onUpdated()
                }
            }
        }
    }


    fun showLocationIsDisabledAlert(context: Context) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setTitle("Enable Location Providers")
            setMessage("The auto-location feature relies on at least one location provider")
            setCancelable(false)
            setPositiveButton("ENABLE PROVIDERS") { _, _ ->
                onGoToLocationSetting()
                onInvalidComplete()
            }
            setNegativeButton("STOP AUTO-LOCATION") { _, _ -> onInvalidComplete() }

        }.create().show()
    }

    //-------
    // Data
    //-------

    fun getLastPlace(): LiveData<PlaceItem?> = placeRepository.getLastPlace()

    suspend fun insert(placeItem: PlaceItem) = placeRepository.insert(placeItem)

    suspend fun  getPlaceItem(id: Long) = placeRepository.getPlaceItem(id)

    suspend fun  getPlaces() = placeRepository.places()

    suspend fun exists(locality: String) = placeRepository.exists(locality)

    suspend fun update(placeItem: PlaceItem) = placeRepository.update(placeItem)

    suspend fun getAutoLocation(isAutoLocation: Boolean) = placeRepository.getAutoLocation(isAutoLocation)

    suspend fun delete(placeItem: PlaceItem) = placeRepository.delete(placeItem)




    //------------------
    // ViewModelFactory
    //------------------

    class Factory(
        private val weatherRepository: WeatherRepository,
        private val placeRepository: PlaceRepository,
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(weatherRepository, placeRepository, application) as T
        }
    }


}