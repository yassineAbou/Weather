package com.yassineabou.weather.ui.listLocations

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
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
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.anyShouldShowRationale
import com.fondesa.kpermissions.coroutines.flow
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.yassineabou.weather.R
import com.yassineabou.weather.data.model.Location
import com.yassineabou.weather.databinding.FragmentListLocationsBinding
import com.yassineabou.weather.ui.ApiStatus
import com.yassineabou.weather.ui.Event
import com.yassineabou.weather.ui.MainViewModel
import com.yassineabou.weather.ui.addLocation.AddLocationDialogFragment
import com.yassineabou.weather.util.clearReference
import com.yassineabou.weather.util.isLocationEnabled
import com.yassineabou.weather.util.showErrorCoordinatesDialog
import com.yassineabou.weather.util.showGrantedToast
import com.yassineabou.weather.util.showLocationIsDisabledAlert
import com.yassineabou.weather.util.showPermanentlyDeniedDialog
import com.yassineabou.weather.util.showRationaleDialog
import com.yassineabou.weather.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListLocationsFragment : Fragment(R.layout.fragment_list_locations) {

    private val fragmentListLocationsBinding by viewBinding(FragmentListLocationsBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()

    private val permissionRequest by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build()
    }

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val lastLocation = p0.lastLocation
            lastLocation?.let {
                mainViewModel.getGeoCoding(it.latitude, it.longitude)
            }
        }
    }

    private val adapter by lazy {
        ListLocationsAdapter(object : LocationActions {
            override fun delete(location: Location) {
                mainViewModel.delete(location)
            }

            override fun select(location: Location) {
                mainViewModel.selectLocation(location)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        fragmentListLocationsBinding.run {
            listLocations.adapter = adapter
            listLocations.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            listLocations.clearReference(viewLifecycleOwner.lifecycle)

            addLocation.setOnClickListener {
                mainViewModel.updateEvent(Event(isAddLocationPressed = true))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.event.collectLatest {
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
                mainViewModel.geocodingApiStatus.collectLatest {
                    it?.let {
                        if (it == ApiStatus.ERROR) {
                            Toast.makeText(
                                requireContext(),
                                R.string.auto_location_error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isConnected.collectLatest { isConnected ->
                    if (isConnected) {
                        if (mainViewModel.toggled.firstOrNull() == true && permissionRequest.checkStatus().allGranted()) { // ktlint-disable max-line-length
                            addAutoLocation()
                        }
                    }
                    if (mainViewModel.weatherApiStatus.value == ApiStatus.ERROR) {
                        mainViewModel.updateWeatherApiStatus(null)
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
                        mainViewModel.updateWeatherApiStatus(ApiStatus.NONE)
                        mainViewModel.setToolbarTitle("")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                permissionRequest.flow().collectLatest { result ->
                    when {
                        result.anyPermanentlyDenied() -> {
                            mainViewModel.toggle(false)
                            requireContext().showPermanentlyDeniedDialog()
                        }
                        result.anyShouldShowRationale() -> {
                            requireContext().showRationaleDialog(
                                permissionRequest = permissionRequest,
                                toggle = { mainViewModel.toggle(false) }
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
        val menuItem = menu.findItem(R.id.toggled)
        val switchCompat = menuItem.actionView as SwitchCompat

        switchCompat.apply {
            setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    mainViewModel.toggle(true)
                } else {
                    mainViewModel.toggle(false)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.toggled.collectLatest { toggled ->
                        switchCompat.isChecked = toggled
                        if (toggled) {
                            thumbDrawable.setTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.orange
                                )
                            )
                            trackDrawable.setTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.orange_100
                                )
                            )

                            requestPermission()
                            if (permissionRequest.checkStatus().allGranted()) {
                                addAutoLocation()
                            }
                        } else {
                            thumbDrawable.setTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.grey_200
                                )
                            )
                            trackDrawable.setTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.grey_500
                                )
                            )

                            if (permissionRequest.checkStatus().allGranted()) {
                                mainViewModel.getAutoLocation {
                                    it?.let { mainViewModel.delete(location = it) }
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
                when (requireContext().isLocationEnabled()) {
                    false -> mainViewModel.updateEvent(Event(hasLocationServiceError = true))
                    true -> {
                        if (mainViewModel.locationGeocoder.value == null) {
                            getCurrentLocation()
                        } else {
                            mainViewModel.locationGeocoder.value?.let {
                                mainViewModel.addLocation(
                                    it
                                )
                            }
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
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0).apply {
            setMinUpdateIntervalMillis(0)
            setMaxUpdates(1)
            setDurationMillis(15000)
        }.build()
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun showLocationIsDisabledAlert() {
        requireContext().showLocationIsDisabledAlert(
            enableLocationProviders = {
                mainViewModel.updateEvent(Event(hasLocationServiceError = false))
                mainViewModel.updateEvent(Event(isEnableProvidersClicked = true))
            },
            stopAutoLocation = {
                mainViewModel.updateEvent(Event(hasLocationServiceError = false))
                mainViewModel.toggle(false)
            }
        )
    }

    private fun showErrorCoordinatesDialog() {
        requireContext().showErrorCoordinatesDialog(
            tryAgain = {
                mainViewModel.updateEvent(Event(isAddLocationPressed = true))
                mainViewModel.updateEvent(Event(hasErrorCoordinates = false))
            },
            close = {
                mainViewModel.updateEvent(Event(hasErrorCoordinates = false))
            }
        )
    }

    private fun navigateToLocationSettings() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        mainViewModel.updateEvent(Event(isEnableProvidersClicked = false))
    }

    private fun requestPermission() {
        if (permissionRequest.checkStatus().allGranted()) {
            return
        } else {
            permissionRequest.send()
        }
    }
}
