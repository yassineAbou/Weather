package com.example.weather.ui.add_location

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.weather.ListLocationsEvent
import com.example.weather.MainViewModel
import com.example.weather.R


class AddLocationDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val dialogView = inflater.inflate(R.layout.fragment_add_location_dialog, null)
            val editText = dialogView.findViewById<EditText>(R.id.editText)


            builder
                .setView(dialogView)
                .setPositiveButton(
                "Add"
                ) { _, _ ->
                    mainViewModel.getCoordinatesByLocation(requireContext(), editText.text.toString())
                }
                .setNegativeButton(
                "Cancel"
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.changeListLocationsEvent(ListLocationsEvent(isAddLocationPressed = false))
    }
}