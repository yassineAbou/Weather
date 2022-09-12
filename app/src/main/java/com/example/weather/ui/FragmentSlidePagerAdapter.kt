package com.example.weather.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.ui.list_locations.ListLocationsFragment
import com.example.weather.ui.current_weather.CurrentWeatherFragment
import com.example.weather.ui.daily_forecast.DailyForecastFragment
import com.example.weather.ui.hourly_forecast.HourlyForecastFragment

class FragmentSlidePagerAdapter(fa: FragmentActivity) :   FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ListLocationsFragment()
            1 -> CurrentWeatherFragment()
            2 -> HourlyForecastFragment()
            else -> DailyForecastFragment()
        }
    }

}