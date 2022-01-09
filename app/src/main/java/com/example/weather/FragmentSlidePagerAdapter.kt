package com.example.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.place.LocationFragment
import com.example.weather.current_weather.TodayFragment
import com.example.weather.daily_forecast.Next7DaysFragment
import com.example.weather.hourly_forecast.Next48HoursFragment

class FragmentSlidePagerAdapter(fa: FragmentActivity) :   FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LocationFragment()
            1 -> TodayFragment()
            2 -> Next48HoursFragment()
            else -> Next7DaysFragment()
        }
    }

}