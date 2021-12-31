package com.example.weather




import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FragmentLocationBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*


class LocationFragment : Fragment() {

    private var autocompleteFragment: AutocompleteSupportFragment? = null
    private lateinit var placesClient: PlacesClient
    private lateinit var binding: FragmentLocationBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


       // binding.viewModel = viewModel
       //binding.lifecycleOwner = viewLifecycleOwner



        val adapter = PlaceItemsAdapter()

        binding.recyclerView.adapter = adapter
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = manager

        sharedViewModel.placeItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }

        })

        // TODO: 12/18/2021 refactor the code autocomplete fragment
        autocompleteFragment = ((childFragmentManager.findFragmentById(R.id.autocomplete_support_fragment)
                as AutocompleteSupportFragment?))

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.AUTOCOMPLETE_API_KEY);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(requireContext())



        autocompleteFragment?. let {
            it.setTypeFilter(TypeFilter.CITIES)
            it.setPlaceFields(
                Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.PHOTO_METADATAS
                )
            )
            it.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onError(p0: Status) {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }

                override fun onPlaceSelected(p0: Place) {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }

            })
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
                 val root: View = autocompleteFragment?.view!!
                 root.findViewById<View>(R.id.places_autocomplete_search_input).performClick()
             }

         }

        return super.onOptionsItemSelected(item)
    }










}