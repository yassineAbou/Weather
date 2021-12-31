package com.example.weather

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.Constants.OPENWEATHERMAP_ID
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherResult
import kotlinx.coroutines.launch

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class SharedViewModel : ViewModel() {



    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<WeatherApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<WeatherApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _weather = MutableLiveData<WeatherResult>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val weather: LiveData<WeatherResult>
        get() = _weather

     fun getWeather() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                _weather.value = WeatherApi.RETROFIT_SERVICE.getWeatherApi(
                    lat = "29.696901",
                    lon= "-9.733198",
                    appid = OPENWEATHERMAP_ID,
                    unit = "metric"
                )
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }




    private var _placeItems = MutableLiveData(mutableListOf<PlaceItem>())
    val placeItems: MutableLiveData<MutableList<PlaceItem>> = _placeItems

    private val _isEnabled = MutableLiveData<Boolean> ()
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

      fun onInvalid() {
        _isEnabled.value = true
    }

    private fun onInvalidComplete() {
        _isEnabled.value = false
    }


    private val _goToLocationSettings = MutableLiveData<Boolean> ()
    val goToLocationSettings: LiveData<Boolean>
        get() = _goToLocationSettings

    private fun onGoToLocationSetting() {
        _goToLocationSettings.value = true
    }

    fun onGoComplete() {
        _isEnabled.value = false
    }

    fun addPlaceItem(placeItem: PlaceItem) {
        _placeItems.value?.add(placeItem)
        _placeItems.notifyObserver()
    }

    fun addAutoPlaceItem(autoPlaceItem: PlaceItem) {
        _placeItems.value?.add(0,autoPlaceItem)
        _placeItems.notifyObserver()
    }

    fun removeAutoPlaceItem() {
        if (_placeItems.value?.isNotEmpty() == true) {
            _placeItems.value?.removeAt(0)
            _placeItems.notifyObserver()
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






}