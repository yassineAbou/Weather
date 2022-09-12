package com.example.weather.ui.list_locations




import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
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
import com.example.weather.ListLocationsEvent
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.ApiStatus
import com.example.weather.data.model.Location
import com.example.weather.databinding.FragmentListLocationsBinding
import com.example.weather.network.ConnectionState
import com.example.weather.ui.add_location.AddLocationDialogFragment
import com.example.weather.util.hasLocationPermissions
import com.example.weather.util.isLocationEnabled
import com.example.weather.util.viewBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


private const val TAG = "LocationFragment"

@AndroidEntryPoint
class ListLocationsFragment : Fragment(R.layout.fragment_list_locations), EasyPermissions.PermissionCallbacks{

    private val fragmentListLocationsBinding  by viewBinding(FragmentListLocationsBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val locationCallback : LocationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val location = p0.lastLocation
            mainViewModel.getGeoCoding(location.latitude, location.longitude)
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
                        Log.e(TAG, "isEnableProvidersClicked: $isEnableProvidersClicked")
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
                                "Auto-location is not working properly, please make sure you connect to the internet ",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.connectionState.collectLatest { connectionState ->
                    if(connectionState == ConnectionState.Fetched)  {
                        if (mainViewModel.isChecked.first()) {
                            addAutoLocation()
                        }
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.listLocationsFlow.collectLatest {
                    Log.e(TAG, "listLocationsFlow size is ${it.size}")
                    adapter.submitList(it)
                    fragmentListLocationsBinding.listLocations.scrollToPosition(0)
                    if (it.isEmpty()) {
                        mainViewModel.changeWeatherApiStatus(ApiStatus.IDLE)
                        mainViewModel.setLocationName("")
                    }
                }
            }
        }





    }

    private fun navigateAddLocationDialog() {
        AddLocationDialogFragment().show(parentFragmentManager, "AddLocationDialogFragment")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.auto_location_menu, menu)
        val menuItem = menu.findItem(R.id.is_checked)
        val switchCompat = menuItem.actionView as SwitchCompat

        switchCompat.apply {

            setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked : Boolean  ->
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
                            if (hasLocationPermissions(requireContext())) {
                                addAutoLocation()
                            }

                        } else {

                            thumbDrawable.setTint(ContextCompat.getColor(requireContext(),R.color.gray200))
                            trackDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.gray300))

                            if (hasLocationPermissions(requireContext())) {
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

    @AfterPermissionGranted(REQUEST_CODE_LOCATION_PERMISSION)
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

    private fun requestLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            numUpdates = 1
            priority = PRIORITY_HIGH_ACCURACY
            setExpirationDuration(15000)
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun showLocationIsDisabledAlert() {
       activity?.let {
           val builder = AlertDialog.Builder(activity)
           builder.apply {
               setTitle("Enable Location Providers")
               setMessage("The auto-location feature relies on at least one location provider")
               setCancelable(false)
               setPositiveButton("ENABLE PROVIDERS") { _, _ ->
                   mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasLocationServiceError = false))
                   mainViewModel.changeListLocationsEvent(ListLocationsEvent(isEnableProvidersClicked = true))
               }
               setNegativeButton("STOP AUTO-LOCATION") { _, _ ->
                   mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasLocationServiceError = false))
                   mainViewModel.onIsCheckedChange(false)

               }
         }.create().show()
     }
   }

   private fun showErrorCoordinatesDialog() {

       activity?.let {
          val builder = AlertDialog.Builder(it)
             builder.apply {
                setTitle("Error")
                setMessage("Make sure you write the location correctly and internet connexion is not missing")
                setCancelable(false)
                setPositiveButton(
               "TRY AGAIN"
                ) { _, _ ->
                     mainViewModel.changeListLocationsEvent(ListLocationsEvent(isAddLocationPressed = true))
                     mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasErrorCoordinates = false))
                }
                setNegativeButton(
                "CLOSE"
                ) { _, _ ->
                     mainViewModel.changeListLocationsEvent(ListLocationsEvent(hasErrorCoordinates = false))
                }

             }
            builder.create().show()
      }
   }


   private fun navigateToLocationSettings() {
      startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
      mainViewModel.changeListLocationsEvent(ListLocationsEvent(isEnableProvidersClicked = false))
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
       grantResults: IntArray
   ) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
   }

   override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
      mainViewModel.onIsCheckedChange(false)
       if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
           AppSettingsDialog.Builder(this).build().show()
       }
   }

   override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
   }

   private fun requestPermission() {
      if (hasLocationPermissions(requireContext())) {
           return
      }
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
          EasyPermissions.requestPermissions(
     this,
  "You need to accept location permissions to use auto location.",
          REQUEST_CODE_LOCATION_PERMISSION,
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION
         )
      } else {
          EasyPermissions.requestPermissions(
     this,
  "You need to accept location permissions to use auto location.",
          REQUEST_CODE_LOCATION_PERMISSION,
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.ACCESS_BACKGROUND_LOCATION
          )
      }
  }



}

private const val REQUEST_CODE_LOCATION_PERMISSION = 0