package com.example.medicineschedule.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.medicineschedule.*
import com.example.medicineschedule.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),View.OnClickListener{
    private lateinit var Viewbinding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        Viewbinding = FragmentHomeBinding.inflate(inflater, container, false)
        return Viewbinding.root



        }
    private fun uiViews() {

        Viewbinding.addDose.setOnClickListener(this)
        Viewbinding.addMeasurement.setOnClickListener(this)
        Viewbinding.addReminder.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.addDose -> {
                val doseIntent = Intent(getActivity(), AddDose::class.java)
                startActivity(doseIntent)
            }

            R.id.addMeasurement -> {
                val measurementIntent = Intent(getActivity(), AddMeasurements::class.java)
                startActivity(measurementIntent)
            }
            R.id.addReminder->{
                val reminderIntent = Intent(getActivity(), AddMedication::class.java)
                startActivity(reminderIntent)

            }
    }
}}