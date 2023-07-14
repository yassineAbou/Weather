package com.yassineabou.weather.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.yassineabou.weather.R
import com.yassineabou.weather.databinding.ActivityMainBinding
import com.yassineabou.weather.util.viewBinding
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
                    if (it == null) {
                        mainViewModel.getSelectedLocation()?.let { location ->
                            mainViewModel.getWeather(location.latitude, location.longitude)
                            mainViewModel.setToolbarTitle(location.locality.substringBefore(','))
                        }
                    }
                }
            }
        }
    }

    private fun setupViewpager2() {
        activityMainBinding.run {
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
        activityMainBinding.run {
            toolbar.title = mainViewModel.toolbarTitle.value
            toolbar.subtitle = getString(R.string.toolbar_daily_forecast)
        }
    }

    private fun toolbarHourlyForecast() {
        activityMainBinding.run {
            toolbar.title = mainViewModel.toolbarTitle.value
            toolbar.subtitle = getString(R.string.toolbar_hourly_forecast)
        }
    }

    private fun toolbarCurrentWeather() {
        activityMainBinding.run {
            toolbar.title = mainViewModel.toolbarTitle.value
            toolbar.subtitle = getString(R.string.toolbar_current_weather)
        }
    }

    private fun toolbarListLocations() {
        activityMainBinding.run {
            toolbar.title = getString(R.string.toolbar_list_locations)
            toolbar.subtitle = ""
        }
    }
}
