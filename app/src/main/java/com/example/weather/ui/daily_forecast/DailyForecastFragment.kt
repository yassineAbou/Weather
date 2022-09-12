package com.example.weather.ui.daily_forecast

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

import com.example.weather.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private val fragmentDailyForecastBinding by viewBinding(com.example.weather.databinding.FragmentDailyForecastBinding::bind)
    private val dailyForecastViewModel: DailyForecastViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val adapter by lazy { ListDailyForecastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentDailyForecastBinding.apply {

            dailyForecastViewModel = dailyForecastViewModel
            lifecycleOwner = viewLifecycleOwner
            listDailyForecast.adapter = adapter
            listDailyForecast.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weatherApiStatus.collectLatest {
                    it?.let {
                        when (it) {
                            ApiStatus.LOADING -> {
                                hideListDailyForecast()
                                fragmentDailyForecastBinding.connectionStatus.setImageResource(R.drawable.loading_animation)
                            }
                            ApiStatus.DONE -> {
                                showListDailyForecast()
                            }
                            else -> {
                                hideListDailyForecast()
                                dailyForecastViewModel.displayDailyForecast(null)
                                fragmentDailyForecastBinding.connectionStatus.setImageResource(R.drawable.ic_connection_error)
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weather.collectLatest {
                    it?.let {
                        dailyForecastViewModel.displayDailyForecast(it.daily)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dailyForecastViewModel.listDailyForecast.collect {
                    it?.let {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun showListDailyForecast() {
        fragmentDailyForecastBinding.apply {
            connectionStatus.visibility = View.GONE
            listDailyForecast.visibility = View.VISIBLE
        }
    }

    private fun hideListDailyForecast() {
        fragmentDailyForecastBinding.apply {
            connectionStatus.visibility = View.VISIBLE
            listDailyForecast.visibility = View.GONE
        }
    }

}