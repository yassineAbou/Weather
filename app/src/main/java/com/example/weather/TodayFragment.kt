package com.example.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weather.databinding.FragmentTodayBinding


class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)



        sharedViewModel.status.observe(viewLifecycleOwner, { status ->
            when (status.name) {
                "LOADING" -> binding.todayStatus?.setImageResource(R.drawable.loading_animation)
                "DONE" -> {
                    binding.todayStatus?.visibility  = View.GONE
                    binding.todayGroup?.visibility  = View.VISIBLE
                }
                "ERROR" -> binding.todayStatus?.setImageResource(R.drawable.ic_connection_error)
            }
        })

        sharedViewModel.weather.observe(viewLifecycleOwner, { weatherResult ->
            if (weatherResult != null) {

                bindImage(
                    binding.todayWeatherImg,
                           "https://openweathermap.org/img/wn/${weatherResult.current.weather[0].icon}.png"
                )
                binding.apply {
                    with(weatherResult) {
                        temperature.text = "${current.temp.toInt()}°"
                        humidity.text = "${current.humidity}%"
                        wind.text = "${current.wind_speed}m/s"
                    }

                }
            }
        })
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }






}