package com.example.weather.hourly_forecast

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MainViewModel
import com.example.weather.R

import com.example.weather.databinding.FragmentNext48HoursBinding


class Next48HoursFragment : Fragment() {

    private lateinit var binding: FragmentNext48HoursBinding
    private val next48HoursViewModel: Next48HoursViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNext48HoursBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


            setupViewModelDataBinding()
            setupAdapter()

            val weatherResult = mainViewModel.getWeatherResult()
            weatherResult?.let { next48HoursViewModel.displayHourlyWeather(weatherResult.hourly) }

            return binding.root

    }

    private fun setupViewModelDataBinding() {
        binding.apply {
            next48HoursViewModelBind = next48HoursViewModel
            lifecycleOwner = viewLifecycleOwner
        }

    }

    private fun setupAdapter() {
        val adapter = HourlyItemsAdapter()
        binding.hourlyRecyclerView.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.hourlyRecyclerView.layoutManager = manager
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }



}