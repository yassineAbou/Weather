package com.example.weather.current_weather

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.databinding.FragmentTodayBinding
import kotlinx.coroutines.flow.collectLatest


class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding
    private val todayViewModel: TodayViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(inflater, container, false)


          setupViewModelDataBinding()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.status.collectLatest {
                it?.let {
                    when (it.name) {
                        "LOADING" -> {
                            hideTodayGroup()
                            binding.todayStatus.setImageResource(R.drawable.loading_animation)
                        }
                        "DONE" -> {
                            showTodayGroup()
                        }
                        "ERROR" -> {
                            hideTodayGroup()
                            binding.todayStatus.setImageResource(R.drawable.ic_connection_error)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.weather.collectLatest {
                it?.let {
                    todayViewModel.displayCurrentWeather(it.current)
                }
            }
        }

        return binding.root
    }

    private fun showTodayGroup() {
        binding.todayStatus.visibility = View.GONE
        binding.todayGroup.visibility = View.VISIBLE
    }

    private fun hideTodayGroup() {
        binding.todayStatus.visibility = View.VISIBLE
        binding.todayGroup.visibility = View.GONE
    }

    private fun setupViewModelDataBinding() {
        binding.apply {
            todayViewModelBind = todayViewModel
            lifecycleOwner = viewLifecycleOwner
        }

    }


}