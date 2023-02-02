package com.example.weather.ui.addLocation

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.weather.R
import com.example.weather.ui.Event
import com.example.weather.ui.MainViewModel

class AddLocationDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_add_location_dialog, null)
            val editText = dialogView.findViewById<EditText>(R.id.editText)

            builder
                .setView(dialogView)
                .setPositiveButton(
                    R.string.add
                ) { _, _ ->
                    mainViewModel.getCoordinatesFromString(
                        requireContext(),
                        editText.text.toString()
                    )
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.updateEvent(Event(isAddLocationPressed = false))
    }
}
