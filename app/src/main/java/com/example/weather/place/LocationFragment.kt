package com.example.weather.place




import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.UiState
import com.example.weather.database.PlaceItem
import com.example.weather.databinding.FragmentLocationBinding
import com.example.weather.util.isLocationEnabled
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "LocationFragment"

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


        val adapter = PlaceItemsAdapter(
            PlaceItemListener { place ->
             mainViewModel.removePlaceItem(place)
            },
            PlaceItemListener2 { place2 ->
               mainViewModel.checkNewItemAdded(place2)
            }
        )

        binding.recyclerView.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = manager

        binding.fab.setOnClickListener {
            mainViewModel.onChangeUiState(UiState(NavigateToNewLocationDialog = true))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect { uiState ->
                    when {
                        uiState.NavigateToNewLocationDialog -> showNewCityDialog()
                        uiState.ShowAlertDialog -> showLocationIsDisabledAlert()
                        uiState.ShowErrorDialog -> showErrorDialog()
                        uiState.GoToLocationSettings -> locationSettings()
                    }
                }
            }
         }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.placeItems.collectLatest {
                adapter.submitList(it)
                if (it.isEmpty()) {
                    mainViewModel.onErrorStatus()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getLastPlace().collectLatest {
                if (mainViewModel.uiState.value.GetLastItem) {
                    it?.let {
                        mainViewModel.checkNewItemAdded(it)
                        mainViewModel.onChangeUiState(UiState(GetLastItem = false))
                    }
                }
            }
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.location_menu, menu)
        val menuItem = menu.findItem(R.id.my_switch)
        val mySwitch = menuItem.actionView as SwitchCompat
        viewLifecycleOwner.lifecycleScope.launch {

            mainViewModel.selected.collectLatest {
                mySwitch.isChecked = it

            }


        }


        mySwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked : Boolean  ->
            if (isChecked) {
                mainViewModel.onSelectAutoLocation(true)
                autoLocation()
            } else {
                mainViewModel.onSelectAutoLocation(false)
                mainViewModel.removeAutoPlaceItem()
            }
        }
    }



    private fun showNewCityDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = NewPlaceDialogFragment()
        dialog.show(parentFragmentManager, "NoticeDialogFragment")

    }

   fun autoLocation() {
        when (isLocationEnabled(requireContext())) {
            false -> mainViewModel.onChangeUiState(UiState(ShowAlertDialog = true))
            else -> mainViewModel.autoLocation.value?.let { mainViewModel.addPlaceItem(it) }
        }
    }

    fun showLocationIsDisabledAlert() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle("Enable Location Providers")
            setMessage("The auto-location feature relies on at least one location provider")
            setCancelable(false)
            setPositiveButton("ENABLE PROVIDERS") { _, _ ->
                mainViewModel.onChangeUiState(UiState(ShowAlertDialog = false))
                mainViewModel.onChangeUiState(UiState(GoToLocationSettings = true))

            }
            setNegativeButton("STOP AUTO-LOCATION") { _, _ ->
                mainViewModel.onSelectAutoLocation(false)
                mainViewModel.onChangeUiState(UiState(ShowAlertDialog = false))

            }

        }.create().show()
    }

    fun showErrorDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("Error")
            setMessage("Make sure you write the city correctly and internet connexion is not missing")
            setCancelable(false)
            setPositiveButton("TRY AGAIN") { _, _ ->
                mainViewModel.onChangeUiState(UiState(NavigateToNewLocationDialog = true))
                mainViewModel.onChangeUiState(UiState(ShowErrorDialog = false))
            }
            setNegativeButton("CLOSE") { _, _ ->
                mainViewModel.onChangeUiState(UiState(ShowErrorDialog = false))
            }

        }.create().show()
    }


    private fun locationSettings() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }


    override fun onStart() {
        if (mainViewModel.uiState.value.GoToLocationSettings) {
            mainViewModel.onChangeUiState(UiState(GoToLocationSettings = false))
        }
        mainViewModel.readSelected {
            if (it && isLocationEnabled(requireContext())) {
                mainViewModel.autoLocation.value?.let { mainViewModel.addPlaceItem(it) }
            }
        }
       super.onStart()
    }


}