package com.example.medicineschedule.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.*
import com.example.medicineschedule.databinding.FragmentAavailableFormBinding
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.viewModels.HomeRecVieeModel

class HomeFragment : Fragment(){

    lateinit var viewModel: HomeRecVieeModel

    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            HomeRecVieeModel::class.java)
        binding.viewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.viewModel= viewModel
        }
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            val intent = Intent(context, AddDose::class.java)
            startActivity(intent)
        }

        binding.searchView2.setOnClickListener {

            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)

        }
    }
    private fun uiViews() {

        binding.addDose.setOnClickListener{
            onClick(it)
        }
        binding.addMeasurement.setOnClickListener{
            onClick(it)
        }
        binding.addReminder.setOnClickListener{
            onClick(it)
        }
    }
    fun onClick(v: View?) {
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