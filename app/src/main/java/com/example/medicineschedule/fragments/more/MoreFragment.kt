package com.example.medicineschedule.fragments.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.databinding.FragmentMoreBinding
import com.example.medicineschedule.fragments.medication.MedicationFragment
import com.example.medicineschedule.fragments.pharmacy.PharmacyFragment
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_dictionary.*

class MoreFragment : Fragment() {



    private lateinit var binding: FragmentMoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()

    }
    private fun uiViews() {

        binding.linLayoutNotes.setOnClickListener {
            onClick(it)
        }
        binding.linLayoutReport.setOnClickListener {
            onClick(it)
        }
        binding.linLayoutDrugs.setOnClickListener {
            onClick(it)
        }

        binding.linLayoutMeasurements.setOnClickListener {
            onClick(it)
        }
        binding.linLayoutAppointments.setOnClickListener {
            onClick(it)
        }
    }
    fun onClick(v: View?) {
        when (v?.id) {

            R.id.linLayoutNotes -> {
                val doseIntent = Intent(getActivity(), NotesActivity::class.java)
                startActivity(doseIntent)
            }
            R.id.linLayoutDrugs -> {
                val reportIntent = Intent(activity, DictionaryActivity::class.java)
                startActivity(reportIntent)
            }
            R.id.linLayoutMeasurement -> {
                val reportIntent = Intent(activity, DictionaryActivity::class.java)
                startActivity(reportIntent)
            }
            R.id.linLayoutAppointments -> {
                val reportIntent = Intent(activity, DictionaryActivity::class.java)
                startActivity(reportIntent)
            }
            R.id.linLayoutReport -> {
                
            }
    }

        }
}