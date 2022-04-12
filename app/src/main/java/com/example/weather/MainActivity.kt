package com.example.weather


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.database.PlaceDatabase
import com.example.weather.database.PlaceItem
import com.example.weather.database.PreferencesManager
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.repository.PlaceRepository
import com.example.weather.repository.WeatherRepository
import com.example.weather.util.ConnectivityMonitor
import com.example.weather.util.Constants.MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
import com.example.weather.util.Constants.MY_PERMISSIONS_REQUEST_LOCATION
import com.example.weather.util.isLocationEnabled
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private val weatherRepository = WeatherRepository()
    private val preferencesManager = PreferencesManager(this)
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.Factory(weatherRepository, PlaceRepository(PlaceDatabase.getInstance(
            requireNotNull(this).application).placeDoe), preferencesManager , requireNotNull(this).application)
    }

    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 60
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()

                        val geoCoder = Geocoder(this@MainActivity)
                        val currentLocation = geoCoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )
                        val locality =
                            "${currentLocation.first().locality}, ${currentLocation.first().countryCode}"
                        val lat = location.latitude.toString()
                        val lon = location.longitude.toString()
                        val place = PlaceItem(locality, lat, lon, isAutoLocation = true)
                        mainViewModel.setAutoLocation(place)
                    }

        }
    }


    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("EEE HH:mm")
        return format.format(date)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()


        ConnectivityMonitor(this, this) { isConnected ->
            lifecycle.coroutineScope.launch {
                mainViewModel.getCheckedItem(true).collect {
                    it?.let {
                        mainViewModel.getWeather(it.lat, it.lon)
                        mainViewModel.setLocation(it.locality.substringBefore(','))
                    }
                }
            }
       }


        /*
        lifecycleScope.launchWhenStarted {
            mainViewModel.status.collectLatest {
                it?.let {
                    when (it.name) {
                        "LOADING" -> Toast.makeText(this@MainActivity, "LOADING", Toast.LENGTH_SHORT).show()
                        "DONE" -> Toast.makeText(this@MainActivity, "DONE", Toast.LENGTH_SHORT).show()
                        "ERROR" -> {
                            Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

         */


          setupToolbar()
          setupViewpager2()


    }

    //--------------------------------
    // Setup viewpager and toolbar
    //--------------------------------

    private fun setupViewpager2() {
        val pagerAdapter = FragmentSlidePagerAdapter(this)
        binding.apply {
            viewpager2.adapter = pagerAdapter
            wormDotsIndicator.setViewPager2(binding.viewpager2)

            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> locationBar()
                        1 -> todayBar()
                        2 -> next24HoursBar()
                        3 -> next5DaysBar()
                        else -> return
                    }
                }
            })
        }
    }

    private fun next5DaysBar() {
        binding.apply {
            toolbar.title = mainViewModel.currentLocation.value
            toolbar.subtitle = "Next 7 days, unit:°C"
        }
    }

    private fun next24HoursBar() {
        binding.apply {
            toolbar.title = mainViewModel.currentLocation.value
            toolbar.subtitle = "Next 48 hours, unit:°C"
        }
    }

    private fun todayBar() {
        binding.apply {
            toolbar.title = mainViewModel.currentLocation.value
            toolbar.subtitle = "Today, unit:°C"
        }
    }

    private fun locationBar() {
        binding.apply {
            toolbar.title = " Auto location"
            toolbar.subtitle = ""
        }

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }



    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationProvider?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    //-------------------------------
    // Location permission
    //-------------------------------

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationProvider?.removeLocationUpdates(locationCallback)
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
        } else {
            checkBackgroundLocation()
        }
    }

    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationProvider?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )

                        // Now check background location
                        checkBackgroundLocation()
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()

                    // Check if we are in a state where the user has denied the permission and
                    // selected Don't ask again
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationProvider?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )

                        Toast.makeText(
                            this,
                            "Granted Background Location Permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return

            }
        }
    }





}