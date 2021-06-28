package com.example.weather1.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weather1.adapters.WeatherForecastAdapter;
import com.example.weather1.databinding.FragmentNext5DaysBinding;
import com.example.weather1.models.WeatherForecastResult;
import com.example.weather1.viewmodels.Next5DaysVM;


public class Next5DaysFragment extends Fragment {

    private FragmentNext5DaysBinding binding;
    WeatherForecastAdapter mAdapter;
    Next5DaysVM mNext5Days;




    public Next5DaysFragment() {
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
        binding = FragmentNext5DaysBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mNext5Days  = new ViewModelProvider(this).get(Next5DaysVM.class);
        mNext5Days.getNext5Days();

        configureRecyclerview();
        getForecastWeatherInfo();



        return view;
    }

    private void getForecastWeatherInfo() {
      mNext5Days.postsMutableLiveData.observe(getViewLifecycleOwner(), this::displayForecastWeather);

    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        mAdapter  = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        binding.recyclerForecast5d.setAdapter(mAdapter);

    }



    //--Configuration
    private void configureRecyclerview() {
        binding.recyclerForecast5d.setHasFixedSize(true);
        binding.recyclerForecast5d.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


}