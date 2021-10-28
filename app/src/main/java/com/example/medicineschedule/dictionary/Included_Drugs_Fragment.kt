package com.example.medicineschedule.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentIncludedDrugsBinding
import com.example.medicineschedule.viewModels.AlternateBrand_ViewModel
import com.example.medicineschedule.viewModels.Forms_ViewModel
import com.example.medicineschedule.viewModels.IncludedDrugView_Model

class Included_Drugs_Fragment : Fragment(R.layout.fragment_included__drugs_) {

    lateinit var viewModel: IncludedDrugView_Model

    private lateinit var binding: FragmentIncludedDrugsBinding
    var naController: NavController? =null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            IncludedDrugView_Model::class.java)
        binding.includeDrigViewmodel = viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncludedDrugsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}