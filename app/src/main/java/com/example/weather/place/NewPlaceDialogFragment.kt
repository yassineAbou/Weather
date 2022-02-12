package com.example.weather.place

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.weather.MainViewModel
import com.example.weather.R
import com.google.android.gms.maps.model.LatLng


class NewPlaceDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val dialogView = inflater.inflate(R.layout.fragment_new_place_dialog, null)
            val cityValue = dialogView.findViewById<EditText>(R.id.new_place)



            builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add",
                    DialogInterface.OnClickListener { dialog, id ->
                        mainViewModel.getCityLatitude(requireContext(), cityValue.text.toString())
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*
    private fun getCityLatitude(context: Context, city: String?): LatLng? {
        val geocoder = Geocoder(context, context.resources.configuration.locale)
        var addresses: List<Address>? = null
        var latLng: LatLng? = null
        try {
            addresses = geocoder.getFromLocationName(city, 1)
            val address = addresses[0]
            latLng = LatLng(address.latitude, address.longitude)
            val currentLocation = geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            )

            Toast.makeText(requireContext(), "${latLng.latitude} / ${latLng.longitude} / ${currentLocation.first().countryName}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
             mainViewModel.showErrorDialog(requireContext())
        }
        return latLng
    }

     */

}