package com.example.weather.ui.current_weather


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.ui.ApiStatus
import com.example.weather.ui.MainViewModel
import com.example.weather.R
import com.example.weather.databinding.FragmentCurrentWeatherBinding
import com.example.weather.util.bindImageCurrentWeather
import com.example.weather.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CurrentWeatherFragment : androidx.fragment.app.Fragment(R.layout.fragment_current_weather) {


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
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(R.drawable.loading_animation)
                            }
                            ApiStatus.DONE -> {
                                showCurrentWeatherGroup()
                            }
                            ApiStatus.ERROR -> {
                                currentWeatherViewModel.displayCurrentWeather(null)
                                hideCurrentWeatherGroup()
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(R.drawable.ic_connection_error)
                            }
                            ApiStatus.IDLE -> {
                                currentWeatherViewModel.displayCurrentWeather(null)
                                hideCurrentWeatherGroup()
                                fragmentCurrentWeatherBinding.connectionStatus.setImageResource(R.drawable.ic_baseline_search_24)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weather.collectLatest {
                    it?.let {
                        currentWeatherViewModel.displayCurrentWeather(it.current)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentWeatherViewModel.currentWeather.collectLatest {
                    it?.let {  currentWeather ->
                        fragmentCurrentWeatherBinding.apply {

                            bindImageCurrentWeather(
                                imageCurrentWeather,
                                getString(R.string.open_weather_img, currentWeather.weather[0].icon)
                            )
                            temperature.text = "${currentWeather.temp.toInt()}°"
                            humidity.text = "${currentWeather.humidity}%"
                            wind.text = "${currentWeather.wind_speed}m/s"

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
