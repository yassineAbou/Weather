package com.example.weather.hourly_forecast

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MainViewModel
import com.example.weather.R

import com.example.weather.databinding.FragmentNext48HoursBinding
import kotlinx.coroutines.flow.collectLatest


class Next48HoursFragment : Fragment() {

    private lateinit var binding: FragmentNext48HoursBinding
    private val next48HoursViewModel: Next48HoursViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNext48HoursBinding.inflate(inflater, container, false)

            setupViewModelDataBinding()
            setupAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.status.collectLatest {
                it?.let {
                    when (it.name) {
                        "LOADING" -> {
                            hideHourlyRecyclerView()
                            binding.nextHoursStatus.setImageResource(R.drawable.loading_animation)
                        }
                        "DONE" -> {
                            showHourlyRecyclerView()
                        }
                        "ERROR" -> {
                            hideHourlyRecyclerView()
                            binding.nextHoursStatus.setImageResource(R.drawable.ic_connection_error)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.weather.collectLatest {
                it?.let {
                    next48HoursViewModel.displayHourlyWeather(it.hourly)
                }
            }
        }

            return binding.root

    }

    private fun showHourlyRecyclerView() {
        binding.nextHoursStatus.visibility = View.GONE
        binding.hourlyRecyclerView.visibility = View.VISIBLE
    }

    private fun hideHourlyRecyclerView() {
        binding.nextHoursStatus.visibility = View.VISIBLE
        binding.hourlyRecyclerView.visibility = View.GONE
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




}