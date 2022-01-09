package com.example.weather

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherResult
import com.example.weather.place.PlaceItem
import com.example.weather.repository.WeatherRepository
import com.example.weather.util.Constants
import com.example.weather.util.notifyObserver
import kotlinx.coroutines.launch

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class MainViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _status = MutableLiveData<WeatherApiStatus>()

    val status: LiveData<WeatherApiStatus>
        get() = _status


    private val _weather = MutableLiveData<WeatherResult?>()

    val weather: MutableLiveData<WeatherResult?>
        get() = _weather

    fun getWeather() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                _weather.value = weatherRepository.getWeather(
                    lat = "29.696901",
                    lon= "-9.733198",
                    appid = Constants.OPENWEATHERMAP_ID,
                    unit = "metric"
                )
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
            }
        }
    }

    fun getWeatherResult() = _weather.value


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
    /*
    fun addPlaceItem(placeItem: PlaceItem) {
        _placeItems.value?.add(placeItem)
        _placeItems.notifyObserver()
    }
     */

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


    class Factory(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(weatherRepository) as T
        }
    }



}