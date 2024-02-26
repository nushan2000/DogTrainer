package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SupportRequestDetailsDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SupportRequestDetailsDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_SELECTED_REQUEST = "selectedRequest"

        fun newInstance(selectedRequest: SupportRequest): SupportRequestDetailsDialogFragment {
            val fragment = SupportRequestDetailsDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_SELECTED_REQUEST, selectedRequest)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectedRequest = arguments?.getParcelable<SupportRequest>(ARG_SELECTED_REQUEST)

        return AlertDialog.Builder(requireContext())
            .setTitle("Support Request Details")
            .setMessage("ID: ${selectedRequest?.id}\nReason: ${selectedRequest?.reason}\nTime: ${selectedRequest?.hour}:${selectedRequest?.minute}")
            .setPositiveButton("OK") { _, _ ->
                // Handle positive button click if needed
            }
            .create()
    }
}