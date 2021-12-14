package com.example.medicineschedule.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentAlternateBrandsBinding
import com.example.medicineschedule.viewModels.AlternateBrand_ViewModel

class Alternate_Brands_Fragment : Fragment(R.layout.fragment_alternate__brands_) {

    private lateinit var binding: FragmentAlternateBrandsBinding
    var naController: NavController? =null
    lateinit var viewModel: AlternateBrand_ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlternateBrandsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
           AlternateBrand_ViewModel::class.java)
        binding.aBviewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.aBviewModel= viewModel
        }
        viewModel.drugRecord.observe(viewLifecycleOwner){
            it?.let {
                viewModel.ResValue(it)
                val initialText  = binding.notAvailableText
                initialText.isVisible = it.isEmpty()
            }
        }


    }

}