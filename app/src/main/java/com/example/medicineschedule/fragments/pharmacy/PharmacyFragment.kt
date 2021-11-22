package com.example.medicineschedule.fragments.pharmacy

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.medicineschedule.databinding.FragmentPharmacyBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class PharmacyFragment : Fragment() {
    private lateinit var binding: FragmentPharmacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPharmacyBinding.inflate(inflater, container, false)
        val view = binding.root
        var filterChoice = arrayOf("All Medications","Active Medications")
        var trackChoice = arrayOf("Yesterday","Last 7 days","Last 30 days","Last 60 days","Last 90 days","Last 180 days")
        binding.mdlogTrack.setOnClickListener(View.OnClickListener {
            var alertfilterDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
            alertfilterDialog?.setTitle("Select Track Filter")
            alertfilterDialog?.setSingleChoiceItems(
                filterChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.mdlogTrack.setText(filterChoice[position])

                dialogInterface.dismiss()
            }
            alertfilterDialog?.show()
        })
        binding.imglog.setOnClickListener(View.OnClickListener {
            var alerttrackDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
            alerttrackDialog?.setTitle("Select Filter Options")
            alerttrackDialog?.setSingleChoiceItems(
                trackChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->

                dialogInterface.dismiss()
            }
            alerttrackDialog?.show()
        })

        binding.takeCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(70f)
        }
        binding.missedCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(20f)
        }
        binding.skippedCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(10f)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()

    }

    private fun uiViews() {



    }

}