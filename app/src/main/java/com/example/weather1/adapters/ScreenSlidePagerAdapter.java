package com.example.weather1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weather1.views.Next12HoursFragment;
import com.example.weather1.views.Next5DaysFragment;
import com.example.weather1.views.WeatherFragment;

/**
 * Created by Yassine Abou on 5/20/2021.
 */
public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 3;

    public ScreenSlidePagerAdapter( FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WeatherFragment();
            case 1:
                return new Next12HoursFragment();
            case 2:
                return new Next5DaysFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}

