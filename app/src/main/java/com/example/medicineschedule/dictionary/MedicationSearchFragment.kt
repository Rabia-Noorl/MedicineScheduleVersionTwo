package com.example.medicineschedule.dictionary

import android.R.id
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.DictionaryActivity
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentMedicationSearchBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.InputStream
import android.R.id.input
import com.example.medicineschedule.HomeScreen


class MedicationSearchFragment : Fragment(R.layout.fragment_medication_search) {

    val list =ArrayList<String>()
    private lateinit var binding: FragmentMedicationSearchBinding

    companion object{
        val brandsHints: MutableList<String> = mutableListOf()
    }


    var naController: NavController? =null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController = findNavController()
        main(list)
        val hintAdaper = context?.let { ArrayAdapter<String>(it, R.layout.custom_list_item, R.id.text_view_list_item, HomeScreen.brandsHints) }
        binding.actv.setAdapter(hintAdaper)
        binding.actv.doOnTextChanged { text, start, before, count ->

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

    fun main(args: ArrayList<String>) {
        val inputStream: InputStream = getResources().openRawResource(R.raw.names)
        val inputString = inputStream.bufferedReader().useLines {lines ->
            lines.forEach {
                brandsHints.add(it)
            }
        }
    }
}