package com.example.medicineschedule.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentMedicationSearchBinding
import kotlinx.android.synthetic.main.fragment_medication_search.*

class MedicationSearchFragment : Fragment(R.layout.fragment_medication_search) {

    private lateinit var binding: FragmentMedicationSearchBinding

    var naController: NavController? =null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController = findNavController()

        radioButtonBrand.setOnClickListener {
            onRadioButtonClicked(it)
        }
        radioButtonGeneric.setOnClickListener{
            onRadioButtonClicked(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicationSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radioButtonGeneric ->
                    if (checked) {
                        naController?.navigate(R.id.action_medicationSearchFragment_to_detailed_Info_Fragment)
                    }
                R.id.radioButtonBrand ->
                    if (checked) {
                        naController?.navigate(R.id.action_medicationSearchFragment_to_drug_Info_Fragment2)
                    }
            }
        }
    }

}