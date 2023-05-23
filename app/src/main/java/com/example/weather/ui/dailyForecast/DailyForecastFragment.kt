package com.example.weather.ui.dailyForecast

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
import com.example.weather.R
import com.example.weather.ui.ApiStatus
import com.example.weather.ui.MainViewModel
import com.example.weather.util.clearReference
import com.example.weather.util.viewBinding
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private val fragmentDailyForecastBinding by viewBinding(
        com.example.weather.databinding.FragmentDailyForecastBinding::bind
    )
    private val dailyForecastViewModel: DailyForecastViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val adapter by lazy { ListDailyForecastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentDailyForecastBinding.run {
            lifecycleOwner = viewLifecycleOwner
            listDailyForecast.adapter = adapter
            listDailyForecast.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            listDailyForecast.clearReference(viewLifecycleOwner.lifecycle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.weatherApiStatus.collectLatest {
                    it?.let {
                        when (it) {
                            ApiStatus.LOADING -> {
                                hideListDailyForecast()
                                fragmentDailyForecastBinding.connectionStatus.setImageResource(
                                    R.drawable.loading_animation
                                )
                            }
                            ApiStatus.DONE -> {
                                showListDailyForecast()
                            }
                            ApiStatus.ERROR -> {
                                hideListDailyForecast()
                                dailyForecastViewModel.updateListDailyForecast(null)
                                fragmentDailyForecastBinding.connectionStatus.setImageResource(
                                    R.drawable.ic_connection_error
                                )
                            }
                            ApiStatus.NONE -> {
                                hideListDailyForecast()
                                dailyForecastViewModel.updateListDailyForecast(null)
                                fragmentDailyForecastBinding.connectionStatus.setImageResource(
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
                        dailyForecastViewModel.updateListDailyForecast(it.daily)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dailyForecastViewModel.listDailyForecast.buffer().collect {
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
