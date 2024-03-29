package com.yassineabou.weather.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yassineabou.weather.ui.currentWeather.CurrentWeatherFragment
import com.yassineabou.weather.ui.dailyForecast.DailyForecastFragment
import com.yassineabou.weather.ui.hourlyForecast.HourlyForecastFragment
import com.yassineabou.weather.ui.listLocations.ListLocationsFragment

class FragmentSlidePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListLocationsFragment()
            1 -> CurrentWeatherFragment()
            2 -> HourlyForecastFragment()
            else -> DailyForecastFragment()
        }
    }
}
