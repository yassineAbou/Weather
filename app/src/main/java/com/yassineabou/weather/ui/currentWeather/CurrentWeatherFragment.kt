package com.yassineabou.weather.ui.currentWeather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yassineabou.weather.R
import com.yassineabou.weather.databinding.FragmentCurrentWeatherBinding
import com.yassineabou.weather.ui.ApiStatus
import com.yassineabou.weather.ui.MainViewModel
import com.yassineabou.weather.util.getWeatherDetails
import com.yassineabou.weather.util.viewBinding
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private val fragmentCurrentWeatherBinding by viewBinding(FragmentCurrentWeatherBinding::bind)
    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weatherApiStatus.collectLatest {
                    it?.let {
                        when (it) {
                            ApiStatus.LOADING -> {
                                hideCurrentWeatherGroup()
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(
                                    R.drawable.loading_animation
                                )
                            }
                            ApiStatus.DONE -> {
                                showCurrentWeatherGroup()
                            }
                            ApiStatus.ERROR -> {
                                currentWeatherViewModel.updateCurrentWeather(null)
                                hideCurrentWeatherGroup()
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(
                                    R.drawable.ic_connection_error
                                )
                            }
                            ApiStatus.NONE -> {
                                currentWeatherViewModel.updateCurrentWeather(null)
                                hideCurrentWeatherGroup()
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(
                                    R.drawable.empty_folder
                                )
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weather.buffer().collect {
                    it?.let {
                        currentWeatherViewModel.updateCurrentWeather(it.current)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentWeatherViewModel.currentWeather.buffer().collect {
                    it?.let { currentWeather ->
                        fragmentCurrentWeatherBinding.apply {
                            val weatherIcon = getWeatherDetails(currentWeather.weatherCode.toInt()).first
                            imageCurrentWeather.setImageResource(weatherIcon)
                            temperature.text = "${currentWeather.temperature2m.toInt()}°"
                            humidity.text = "${currentWeather.relativeHumidity2m}%"
                            wind.text = "${currentWeather.windSpeed10m}km/h"
                        }
                    }
                }
            }
        }
    }

    private fun showCurrentWeatherGroup() {
        fragmentCurrentWeatherBinding.apply {
            connectionStatus.visibility = View.GONE
            currentWeatherGroup.visibility = View.VISIBLE
        }
    }

    private fun hideCurrentWeatherGroup() {
        fragmentCurrentWeatherBinding.apply {
            connectionStatus.visibility = View.VISIBLE
            currentWeatherGroup.visibility = View.GONE
        }
    }
}
