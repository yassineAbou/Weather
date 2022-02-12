package com.example.weather.place




import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

import com.example.weather.MainViewModel
import com.example.weather.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {


    private lateinit var binding: FragmentLocationBinding
    private val locationViewModel: LocationViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


       // binding.viewModel = viewModel
       //binding.lifecycleOwner = viewLifecycleOwner


       // val place = PlaceItem("Rabat,MA", "13:50", true)
       // mainViewModel.addAutoPlaceItem(place)



        val adapter = PlaceItemsAdapter(
            PlaceItemListener { place ->
             mainViewModel.removePlaceItem(place)
            },
            PlaceItemListener2 { place2 ->
               mainViewModel.updateIsChecked(place2)
            }
        )

        binding.recyclerView.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = manager


        mainViewModel.getLastPlace().observe(viewLifecycleOwner) {
            if (mainViewModel.isAdded.value == true) {
                it?.let {
                    mainViewModel.updateIsChecked(it)
                    mainViewModel.onUpdatedComplete()
                }

            }

        }

        mainViewModel.navigateToNewLocationDialog.observe(viewLifecycleOwner) {
            if (it == true)
                showNewCityDialog()
        }


        mainViewModel.placeItems.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.location_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

         when (item.itemId) {
             R.id.action_add -> {
                 mainViewModel.onNavigation()
             }
         }

        return super.onOptionsItemSelected(item)
    }

    private fun showNewCityDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = NewPlaceDialogFragment()
        dialog.show(parentFragmentManager, "NoticeDialogFragment")
        mainViewModel.onNavigationComplete()
    }



}