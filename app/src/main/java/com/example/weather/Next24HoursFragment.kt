package com.example.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.weather.databinding.FragmentNext24HoursBinding
import com.example.weather.databinding.FragmentTodayBinding


class Next24HoursFragment : Fragment() {

    private lateinit var binding: FragmentNext24HoursBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNext24HoursBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }



}