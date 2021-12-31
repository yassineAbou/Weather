package com.example.weather

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FragmentNext24HoursBinding
import com.example.weather.databinding.FragmentTodayBinding


class Next24HoursFragment : Fragment() {

    private lateinit var binding: FragmentNext24HoursBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNext24HoursBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        val adapter = Next24HoursAdapter()

        binding.recyclerView2.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView2.layoutManager = manager


        sharedViewModel.status.observe(viewLifecycleOwner, { status ->
            when (status.name) {
                "LOADING" -> binding.nextHoursStatus.setImageResource(R.drawable.loading_animation)
                "DONE" -> {
                    binding.nextHoursStatus.visibility = View.GONE
                    binding.recyclerView2.visibility = View.VISIBLE
                }
                "ERROR" -> binding.nextHoursStatus.setImageResource(R.drawable.ic_connection_error)
            }
        })

        sharedViewModel.weather.observe(viewLifecycleOwner, { weatherResult ->
            if (weatherResult != null) {
                adapter.submitList(weatherResult.hourly)
            }
        })

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)
    }



}