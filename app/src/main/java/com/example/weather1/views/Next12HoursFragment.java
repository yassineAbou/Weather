package com.example.weather1.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weather1.adapters.WeatherNext12Adapter;
import com.example.weather1.databinding.FragmentNext12HoursBinding;
import com.example.weather1.models.Forecast12Hours;
import com.example.weather1.viewmodels.Next12HoursVM;


public class Next12HoursFragment extends Fragment {

    private FragmentNext12HoursBinding binding;
    Next12HoursVM mHoursVM;
    WeatherNext12Adapter mAdapter;

    public Next12HoursFragment() {
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
        binding = FragmentNext12HoursBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mHoursVM = new ViewModelProvider(this).get(Next12HoursVM.class);
        mHoursVM.getNext12Hours();


        configureRecyclerview();
        getWeatherInfo();
        return view;
    }

    private void getWeatherInfo() {
        mHoursVM.postsMutableLiveData.observe(getViewLifecycleOwner(), this::displayForecast12Hours);
    }

    private void displayForecast12Hours(Forecast12Hours forecast12Hours) {
        mAdapter  = new WeatherNext12Adapter(getContext(), forecast12Hours);
        binding.recyclerForecast12h.setAdapter(mAdapter);

    }

    //--Configuration
    private void configureRecyclerview() {
        binding.recyclerForecast12h.setHasFixedSize(true);
        binding.recyclerForecast12h.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }




}