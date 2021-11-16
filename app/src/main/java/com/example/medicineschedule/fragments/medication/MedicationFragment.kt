package com.example.medicineschedule.fragments.medication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.AddDose
import com.example.medicineschedule.AddMeasurements
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.ActivityAddMeasurementsBinding
import com.example.medicineschedule.databinding.ActivityAddMedicationBinding
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.databinding.FragmentMedication2Binding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.example.medicineschedule.viewModels.MedicineRecViewModel

class MedicationFragment : Fragment(R.layout.fragment_medication2) {

    lateinit var viewModel: MedicineRecViewModel

    private lateinit var binding: FragmentMedication2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedication2Binding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        binding.addMedBtn.setOnClickListener{
            val measurementIntent = Intent(getActivity(), AddDose::class.java)
            startActivity(measurementIntent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            MedicineRecViewModel::class.java)


        binding.measurmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.measurmentViewModel= viewModel

//            viewModel.allRemidersobserve(viewLifecycleOwner){
//                it?.let {
//                    viewModel.addFun(it)
//                    Toast.makeText(context, "${it.size} are total records", Toast.LENGTH_SHORT).show()
//
////                    var anim  = binding.homeLottieanim
////                    var getStarted  = binding.textView9
////                    var initialText  = binding.initialTV
////                    anim.isVisible = it.isEmpty()
////                    getStarted.isVisible = it.isEmpty()
////                    initialText.isVisible = it.isEmpty()
//                }
  //          }
        }
        return view
    }

}