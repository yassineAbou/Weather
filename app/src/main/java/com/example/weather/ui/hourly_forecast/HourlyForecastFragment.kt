package com.example.weather.ui.hourly_forecast

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.ApiStatus
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.databinding.FragmentHourlyForecastBinding

import com.example.weather.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HourlyForecastFragment : Fragment(R.layout.fragment_hourly_forecast) {

    private val fragmentHourlyForecastBinding by viewBinding(FragmentHourlyForecastBinding::bind)
    private val hourlyForecastViewModel: HourlyForecastViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val adapter by lazy { ListHourlyForecastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHourlyForecastBinding.apply {

            hourlyForecastViewModel = hourlyForecastViewModel
            lifecycleOwner = viewLifecycleOwner
            listHourlyForecast.adapter = adapter
            listHourlyForecast.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }




        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weatherApiStatus.collectLatest {
                    it?.let {
                        when (it) {
                            ApiStatus.LOADING -> {
                                hideListHourlyForecast()
                                fragmentHourlyForecastBinding.connectionStatus.setImageResource(R.drawable.loading_animation)
                            }
                            ApiStatus.DONE -> {
                                showListHourlyForecast()
                            }
                            else -> {
                                hideListHourlyForecast()
                                hourlyForecastViewModel.displayHourlyForecast(null)
                                fragmentHourlyForecastBinding.connectionStatus.setImageResource(R.drawable.ic_connection_error)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weather.collectLatest {
                    it?.let {
                        hourlyForecastViewModel.displayHourlyForecast(it.hourly)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hourlyForecastViewModel.listHourlyForecastFlow.collect {
                    it?.let {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun showListHourlyForecast() {
        fragmentHourlyForecastBinding.apply {
            connectionStatus.visibility = View.GONE
            listHourlyForecast.visibility = View.VISIBLE
        }
    }

    private fun hideListHourlyForecast() {
        fragmentHourlyForecastBinding.apply {
            connectionStatus.visibility = View.VISIBLE
            listHourlyForecast.visibility = View.GONE
        }
    }

}