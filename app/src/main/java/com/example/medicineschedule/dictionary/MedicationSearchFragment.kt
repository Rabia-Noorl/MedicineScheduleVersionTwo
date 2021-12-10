package com.example.medicineschedule.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentMedicationSearchBinding
import kotlinx.android.synthetic.main.fragment_medication_search.*

class MedicationSearchFragment : Fragment(R.layout.fragment_medication_search) {

    val hints: List<String> = listOf("KANOX 10mg|ml Injection 1s","BITOL SODIUM 500mg Injection 2 Vials","Brufen Plus tablet 200/20 mg 2x10's","NAPA 125mg Suppositories 1x5s","QONZA 10mg Tablet 30s")

    private lateinit var binding: FragmentMedicationSearchBinding


    var naController: NavController? =null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController = findNavController()

        val hintAdaper =
            context?.let { ArrayAdapter<String>(it, R.layout.custom_list_item, R.id.text_view_list_item, hints) }
        binding.actv.setAdapter(hintAdaper)

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