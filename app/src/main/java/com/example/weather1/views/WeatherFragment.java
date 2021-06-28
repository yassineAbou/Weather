package com.example.weather1.views;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather1.R;
import com.example.weather1.apis.IbmWeather;
import com.example.weather1.databinding.FragmentWeatherBinding;
import com.example.weather1.models.CurrentWeather;
import com.example.weather1.utils.Common;
import com.example.weather1.viewmodels.CurrentWeatherVM;


public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    CurrentWeatherVM mCurrentWeatherVM;
    IbmWeather mIbmWeather;

    public WeatherFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

       mCurrentWeatherVM  = new ViewModelProvider(this).get(CurrentWeatherVM.class);
       mCurrentWeatherVM.getCurrentWeather();

        getWeatherInformation();

        return view;


    }

    private void getWeatherInformation() {
     mCurrentWeatherVM.postsMutableLiveData.observe(getViewLifecycleOwner(), this::displayInfo);

    }

    @SuppressLint("DefaultLocale")
    private void displayInfo(CurrentWeather test) {
        TypedArray images = requireActivity().getResources().obtainTypedArray(R.array.images);
        binding.imgWeather.setImageResource(images.getResourceId(test.iconCode, -1));
        images.recycle();

        binding.temperature.setText(getString(R.string.tempValue, String.valueOf(test.getTemperature())));
        binding.wind.setText(getString(R.string.windValue, String.valueOf(test.getWindSpeed())));
        binding.pressure.setText(getString(R.string.pressureValue, String.valueOf(test.getPressureMeanSeaLevel())));
        binding.humidity.setText(getString(R.string.humValue, String.valueOf(test.getRelativeHumidity())));
        binding.sunrise.setText(Common.convertUnixToHour(test.getSunriseTimeUtc()));
        binding.sunset.setText(Common.convertUnixToHour(test.getSunsetTimeUtc()));

        Double lat = Common.currentLocation.getLatitude();
        Double lon = Common.currentLocation.getLongitude();
        binding.geoCoords.setText(getString(R.string.coordValue, String.format("%.2f", lat),
                String.format("%.2f", lon)));

        //Display panel
        binding.groupDetails.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);

    }


}