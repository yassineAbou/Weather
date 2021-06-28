package com.example.weather1.views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weather1.R;
import com.example.weather1.adapters.ScreenSlidePagerAdapter;
import com.example.weather1.databinding.ActivityMainBinding;
import com.example.weather1.utils.Common;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentStateAdapter pagerAdapter;
    private static final String TAG = "MainActivity";
    private LocationManager locationManager;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        configureViewPagerAndToolbar();

        locationIsEnabled();


    }

    //----------------
    // Location
    //----------------

    private void locationIsEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationGps != null) {
                Common.currentLocation = locationGps;
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder. getFromLocation(Common.currentLocation.getLatitude(), Common.currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                   // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    city = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Unable to find location!",Toast.LENGTH_SHORT).show();
            }
        }
      }

    //---------------------
    // UI
    //--------------------

    private void configureViewPagerAndToolbar() {
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        binding.pager.setAdapter(pagerAdapter);

        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        Objects.requireNonNull(getSupportActionBar()).setTitle(city);
                        getSupportActionBar().setSubtitle(Common.convertDateToHour(Common.currentLocation.getTime()));
                        break;
                    case 1:
                        Objects.requireNonNull(getSupportActionBar()).setTitle(city);
                        getSupportActionBar().setSubtitle(R.string.frag2);
                        break;
                    case 2:
                        Objects.requireNonNull(getSupportActionBar()).setTitle(city);
                        getSupportActionBar().setSubtitle(R.string.frag3);
                        break;
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (binding.pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.pager.setCurrentItem(binding.pager.getCurrentItem() - 1);
        }
    }


}