package com.example.weather.daily_forecast

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

import com.example.weather.databinding.FragmentNext7DaysBinding
import kotlinx.coroutines.flow.collectLatest


class Next7DaysFragment : Fragment() {

    private lateinit var binding: FragmentNext7DaysBinding
    private val next7DaysViewModel: Next7DaysViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNext7DaysBinding.inflate(inflater, container, false)

        setupAdapter()
        setupViewModelDataBinding()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.status.collectLatest {
                it?.let {
                    when (it.name) {
                        "LOADING" -> {
                            hideDailyRecyclerview()
                            binding.nextDaysStatus.setImageResource(R.drawable.loading_animation)
                        }
                        "DONE" -> {
                            showDailyRecyclerview()
                        }
                        "ERROR" -> {
                            hideDailyRecyclerview()
                            binding.nextDaysStatus.setImageResource(R.drawable.ic_connection_error)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.weather.collectLatest {
                it?.let {
                    next7DaysViewModel.displayDailyWeather(it.daily)
                }
            }
        }

        return binding.root
    }

    private fun showDailyRecyclerview() {
        binding.nextDaysStatus.visibility = View.GONE
        binding.dailyRecyclerView.visibility = View.VISIBLE
    }

    private fun hideDailyRecyclerview() {
        binding.nextDaysStatus.visibility = View.VISIBLE
        binding.dailyRecyclerView.visibility = View.GONE
    }

    private fun setupViewModelDataBinding() {
        binding.apply {
            next7daysViewModelBind = next7DaysViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setupAdapter() {
        val adapter = DailyItemsAdapter()
        binding.dailyRecyclerView.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.dailyRecyclerView.layoutManager = manager
    }




}