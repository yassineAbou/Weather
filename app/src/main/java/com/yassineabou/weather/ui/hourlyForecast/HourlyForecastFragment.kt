package com.yassineabou.weather.ui.hourlyForecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yassineabou.weather.R
import com.yassineabou.weather.databinding.FragmentHourlyForecastBinding
import com.yassineabou.weather.ui.ApiStatus
import com.yassineabou.weather.ui.MainViewModel
import com.yassineabou.weather.util.clearReference
import com.yassineabou.weather.util.viewBinding
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HourlyForecastFragment : Fragment(R.layout.fragment_hourly_forecast) {

    private val fragmentHourlyForecastBinding by viewBinding(FragmentHourlyForecastBinding::bind)
    private val hourlyForecastViewModel: HourlyForecastViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val adapter by lazy { ListHourlyForecastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHourlyForecastBinding.run {
            lifecycleOwner = viewLifecycleOwner
            listHourlyForecast.adapter = adapter
            listHourlyForecast.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            listHourlyForecast.clearReference(viewLifecycleOwner.lifecycle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weatherApiStatus.collectLatest {
                    it?.let {
                        when (it) {
                            ApiStatus.LOADING -> {
                                hideListHourlyForecast()
                                fragmentHourlyForecastBinding.connectionStatus.setImageResource(
                                    R.drawable.loading_animation
                                )
                            }
                            ApiStatus.DONE -> {
                                showListHourlyForecast()
                            }
                            ApiStatus.ERROR -> {
                                hideListHourlyForecast()
                                hourlyForecastViewModel.updateListHourlyForecast(null)
                                fragmentHourlyForecastBinding.connectionStatus.setImageResource(
                                    R.drawable.ic_connection_error
                                )
                            }
                            ApiStatus.NONE -> {
                                hideListHourlyForecast()
                                hourlyForecastViewModel.updateListHourlyForecast(null)
                                fragmentHourlyForecastBinding.connectionStatus.setImageResource(
                                    R.drawable.empty_folder
                                )
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weather.buffer().collect {
                    it?.let {
                        hourlyForecastViewModel.updateListHourlyForecast(it.hourly)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hourlyForecastViewModel.listHourlyForecast.buffer().collect {
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
