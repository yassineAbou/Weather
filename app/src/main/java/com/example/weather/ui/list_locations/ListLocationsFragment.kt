package com.example.weather.ui.list_locations




import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.ui.ApiStatus
import com.example.weather.ui.ListLocationsEvent
import com.example.weather.ui.MainViewModel
import com.example.weather.R
import com.example.weather.data.model.Location
import com.example.weather.databinding.FragmentListLocationsBinding
import com.example.weather.ui.add_location.AddLocationDialogFragment
import com.example.weather.util.*
import com.example.weather.util.showPermanentlyDeniedDialog
import com.example.weather.util.showRationaleDialog
import com.fondesa.kpermissions.*
import com.fondesa.kpermissions.coroutines.flow
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.gms.location.*
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



@AndroidEntryPoint
class ListLocationsFragment : Fragment(R.layout.fragment_list_locations){

    private val fragmentListLocationsBinding  by viewBinding(FragmentListLocationsBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val permissionRequest by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissionsBuilder(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).build()
        } else {
            permissionsBuilder(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).build()
        }
    }


    private val locationCallback : LocationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val location = p0.lastLocation
            location?.let { mainViewModel.getGeoCoding(it.latitude, it.longitude) }
        }
    }

    private val adapter by lazy {
        ListLocationsAdapter( object : LocationActions {
        override fun deleteLocation(location: Location) {
            mainViewModel.deleteLocation(location)
        }

        override fun selectLocation(location: Location) {
            mainViewModel.selectLocation(location)
        }
    })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        fragmentListLocationsBinding.apply {
            listLocations.adapter = adapter
            listLocations.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            addLocation.setOnClickListener {
                mainViewModel.changeListLocationsEvent(ListLocationsEvent(isAddLocationPressed = true))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.listLocationsEvent.collect {
                    it.apply {
                        when {
                            isAddLocationPressed -> navigateAddLocationDialog()
                            hasLocationServiceError -> showLocationIsDisabledAlert()
                            hasErrorCoordinates -> showErrorCoordinatesDialog()
                            isEnableProvidersClicked -> navigateToLocationSettings()
                        }
                    }
                 }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.geoCodingApiStatus.collectLatest {
                    it?.let {
                        if (it == ApiStatus.ERROR) {
                            Toast.makeText(
                                requireContext(),
                                R.string.auto_location_error,
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isInternetConnectedFlow.collectLatest {
                    if(it)  {
                        if (mainViewModel.isChecked.first() && permissionRequest.checkStatus().allGranted()) {
                            addAutoLocation()
                        }
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.listLocationsFlow.collectLatest {
                    adapter.submitList(it)
                    fragmentListLocationsBinding.listLocations.scrollToPosition(0)
                    if (it.isEmpty()) {
                        mainViewModel.changeWeatherApiStatus(ApiStatus.IDLE)
                        mainViewModel.setLocationName("")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                permissionRequest.flow().collect { result ->
                    when {
                        result.anyPermanentlyDenied() -> {
                            mainViewModel.onIsCheckedChange(false)
                            requireContext().showPermanentlyDeniedDialog()
                        }
                        result.anyShouldShowRationale() -> {
                            requireContext().showRationaleDialog(
                                permissionRequest = permissionRequest,
                                onIsCheckedChange = { mainViewModel.onIsCheckedChange(false) }
                            )
                        }
                        result.allGranted() -> {
                             requireContext().showGrantedToast()
                             addAutoLocation()
                        }

                    }
                }
            }
        }



    }

    private fun navigateAddLocationDialog() {
        AddLocationDialogFragment().show(parentFragmentManager, "AddLocationDialogFragment")
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.auto_location_menu, menu)
        val menuItem = menu.findItem(R.id.is_checked)
        val switchCompat = menuItem.actionView as SwitchCompat

        switchCompat.apply {

            setOnCheckedChangeListener { _: CompoundButton?, isChecked : Boolean  ->
                if (isChecked) {
                    mainViewModel.onIsCheckedChange(true)
                } else {
                    mainViewModel.onIsCheckedChange(false)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.isChecked.collectLatest { isChecked ->
                        switchCompat.isChecked = isChecked
                        if (isChecked) {

                            thumbDrawable.setTint(ContextCompat.getColor(requireContext(),R.color.orange))
                            trackDrawable.setTint(ContextCompat.getColor(requireContext(),R.color.orange100))


                            requestPermission()
                            if (permissionRequest.checkStatus().allGranted()) {
                                addAutoLocation()
                            }

                        } else {

                            thumbDrawable.setTint(ContextCompat.getColor(requireContext(),R.color.gray200))
                            trackDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.gray300))

                            if (permissionRequest.checkStatus().allGranted()) {
                                mainViewModel.getAutoLocation {
                                    it?.let { mainViewModel.deleteLocation(it) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

   private fun addAutoLocation() {
       mainViewModel.getAutoLocation { autoLocation ->
           if (autoLocation == null) {
               when (isLocationEnabled(requireContext())) {
                   false -> mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasLocationServiceError = true))
                   true -> {
                       if (mainViewModel.locationGeoCoder.value == null ) {
                           getCurrentLocation()
                       } else {
                           mainViewModel.locationGeoCoder.value?.let { mainViewModel.addLocation(it) }
                       }
                   }
               }
           }
       }
   }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
       fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
           if (location != null) {
               mainViewModel.getGeoCoding(location.latitude, location.longitude)
           } else {
               viewLifecycleOwner.lifecycleScope.launch {
                   requestLocation()
               }
           }
       }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            numUpdates = 1
            priority = Priority.PRIORITY_HIGH_ACCURACY
            setExpirationDuration(15000)
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun showLocationIsDisabledAlert() {
        requireContext().showLocationIsDisabledAlert(
            enableLocationProviders = {
                mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasLocationServiceError = false))
                mainViewModel.changeListLocationsEvent(ListLocationsEvent(isEnableProvidersClicked = true))
            },
            stopAutoLocation = {
                mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasLocationServiceError = false))
                mainViewModel.onIsCheckedChange(false)
            }
        )
   }

   private fun showErrorCoordinatesDialog() {
       requireContext().showErrorCoordinatesDialog(
          tryAgain = {
              mainViewModel.changeListLocationsEvent(ListLocationsEvent(isAddLocationPressed = true))
              mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasErrorCoordinates = false))
          },
          close = {
              mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasErrorCoordinates = false))
          }
       )

   }


   private fun navigateToLocationSettings() {
      startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
      mainViewModel.changeListLocationsEvent(ListLocationsEvent(isEnableProvidersClicked = false))
   }

   private fun requestPermission() {
     if (permissionRequest.checkStatus().allGranted()) {
           return
      }
      else {
          permissionRequest.send()
      }
  }

}

