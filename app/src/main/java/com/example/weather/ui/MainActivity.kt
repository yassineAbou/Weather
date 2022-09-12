package com.example.weather.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.MainViewModel
import com.example.weather.ApiStatus
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.network.ConnectionState
import com.example.weather.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityMainBinding by viewBinding(ActivityMainBinding::inflate)

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)



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
                mainViewModel.connectionState.collectLatest { connectionState ->
                        if(connectionState == ConnectionState.Fetched)  {
                            if (mainViewModel.weatherApiStatus.value == ApiStatus.ERROR) {
                                mainViewModel.changeWeatherApiStatus(null)
                                Log.e(TAG, "onCreate: TTTTTTTTT")
                            }
                        }
                }
            }
        }





        setSupportActionBar(activityMainBinding.toolbar)
        setupViewpager2()


    }

    private fun setupViewpager2() {
        val pagerAdapter by lazy { FragmentSlidePagerAdapter(this) }
        activityMainBinding.apply {
            viewpager2.adapter = pagerAdapter
            wormDotsIndicator.setViewPager2(activityMainBinding.viewpager2)

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
            toolbar.subtitle = "Next 8 days, unit:°C"
        }
    }

    private fun toolbarHourlyForecast() {
        activityMainBinding.apply {
            toolbar.title = mainViewModel.locationName.value
            toolbar.subtitle = "Next 48 hours, unit:°C"
        }
    }

    private fun toolbarCurrentWeather() {
        activityMainBinding.apply {
            toolbar.title = mainViewModel.locationName.value
            toolbar.subtitle = "Today, unit:°C"
        }
    }

    private fun toolbarListLocations() {
        activityMainBinding.apply {
            toolbar.title = "Auto location"
            toolbar.subtitle = ""
        }
    }

}

private const val TAG = "MainActivity"