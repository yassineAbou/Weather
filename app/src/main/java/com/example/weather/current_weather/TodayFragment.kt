package com.example.weather.current_weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.databinding.FragmentTodayBinding


class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding
    private val todayViewModel: TodayViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

          setupViewModelDataBinding()

          val weatherResult = mainViewModel.getWeatherResult()
          weatherResult?.let { todayViewModel.displayCurrentWeather(it.current) }


        return binding.root
    }

    private fun setupViewModelDataBinding() {
        binding.apply {
            todayViewModelBind = todayViewModel
            lifecycleOwner = viewLifecycleOwner
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }

}