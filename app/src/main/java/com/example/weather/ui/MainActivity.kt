package com.example.weather.ui


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.util.viewBinding
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel: MainViewModel by viewModels()
    private val pagerAdapter by lazy { FragmentSlidePagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        activityMainBinding.viewpager2.adapter = pagerAdapter
        activityMainBinding.wormDotsIndicator.attachTo(activityMainBinding.viewpager2)

        setSupportActionBar(activityMainBinding.toolbar)
        setupViewpager2()


            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.weatherApiStatus.collectLatest {
                            if (it == null ) {
                                mainViewModel.getSelectedLocation()?.let {  location ->
                                    mainViewModel.getWeather(location.latitude, location.longitude)
                                    mainViewModel.setLocationName(location.locality.substringBefore(','))
                                }
                            }
                    }
                }
            }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isInternetConnectedFlow.collectLatest {
                        if(it)  {
                            if (mainViewModel.weatherApiStatus.value == ApiStatus.ERROR) {
                                mainViewModel.changeWeatherApiStatus(null)
                            }
                        }
                }
            }
        }

    }

    private fun setupViewpager2() {
        activityMainBinding.apply {
            viewpager2.adapter = pagerAdapter


            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> toolbarListLocations()
                        1 -> toolbarCurrentWeather()
                        2 -> toolbarHourlyForecast()
                        3 -> toolbarDailyForecast()
                        else -> {}
                    }
                }
            })
        }
    }

    private fun toolbarDailyForecast() {
        activityMainBinding.apply {
            toolbar.title = mainViewModel.locationName.value
            toolbar.subtitle = getString(R.string.toolbar_daily_forecast)
        }
    }

    private fun toolbarHourlyForecast() {
        activityMainBinding.apply {
            toolbar.title = mainViewModel.locationName.value
            toolbar.subtitle = getString(R.string.toolbar_hourly_forecast)
        }
    }

    private fun toolbarCurrentWeather() {
        activityMainBinding.apply {
            toolbar.title = mainViewModel.locationName.value
            toolbar.subtitle = getString(R.string.toolbar_current_weather)
        }
    }

    private fun toolbarListLocations() {
        activityMainBinding.apply {
            toolbar.title = getString(R.string.toolbar_list_locations)
            toolbar.subtitle = ""
        }
    }

}
