package com.example.medicineschedule.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.DictionaryActivity
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentMedicationSearchBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_medication_search.*

class MedicationSearchFragment : Fragment(R.layout.fragment_medication_search) {

    val db = FirebaseFirestore.getInstance()
    val brandsHints: MutableList<String> = mutableListOf()

    private lateinit var binding: FragmentMedicationSearchBinding


    var naController: NavController? =null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController = findNavController()

        db.collection("Medicines").get().addOnSuccessListener {
            it.forEach{
                brandsHints.add(it.id)
            }
            val hintAdaper =
                context?.let { ArrayAdapter<String>(it, R.layout.custom_list_item, R.id.text_view_list_item, brandsHints) }
            binding.actv.setAdapter(hintAdaper)
        }

        binding.actv.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            DictionaryActivity.drugName = selectedItem
            binding.actv.setText("")
            naController?.navigate(R.id.action_medicationSearchFragment_to_drug_Info_Fragment2)
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
//
//    fun onRadioButtonClicked(view: View) {
//        if (view is RadioButton) {
//            val checked = view.isChecked
//
//            when (view.getId()) {
//                R.id.radioButtonGeneric ->
//                    if (checked) {
//                        naController?.navigate(R.id.action_medicationSearchFragment_to_detailed_Info_Fragment)
//                    }
//                R.id.radioButtonBrand ->
//                    if (checked) {
//
//                    }
//            }
//        }
//    }

}