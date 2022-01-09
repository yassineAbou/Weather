package com.example.weather.place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.util.Constants
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var placesClient: PlacesClient
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val autocompleteFragment = ((childFragmentManager.findFragmentById(R.id.autocomplete_support_fragment)
                as AutocompleteSupportFragment?))


        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.AUTOCOMPLETE_API_KEY);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(requireContext())




        autocompleteFragment!!.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment!!.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            }

        })

        return binding.root
    }



}