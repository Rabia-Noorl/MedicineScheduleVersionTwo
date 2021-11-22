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
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentMedication2Binding
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
            viewModel.allRemiders.observe(viewLifecycleOwner){
                it?.let {
                    var recViewList = ArrayList<ReminderTracker>()
                    it.forEach{
                        if(it.types == "med" && !it.deleteFlage)
                        {
                            recViewList.add(it)
                        }
                    }
                    viewModel.addFun(recViewList)
                    var anim  = binding.medLottieanim
                    var initialText  = binding.initialTV
                    anim.isVisible = recViewList.isEmpty()
                    initialText.isVisible = recViewList.isEmpty()
                }
            }
        }
        return view
    }

}