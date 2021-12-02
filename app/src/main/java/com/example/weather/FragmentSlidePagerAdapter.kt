package com.example.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

 class FragmentSlidePagerAdapter(fa: FragmentActivity) :   FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LocationFragment()
            1 -> TodayFragment()
            2 -> Next24HoursFragment()
            else -> Next5DaysFragment()
        }
    }

}