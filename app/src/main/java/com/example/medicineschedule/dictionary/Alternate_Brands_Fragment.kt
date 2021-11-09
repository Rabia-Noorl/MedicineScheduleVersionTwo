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
import com.example.medicineschedule.databinding.FragmentAlternateBrandsBinding
import com.example.medicineschedule.viewModels.AlternateBrand_ViewModel

class Alternate_Brands_Fragment : Fragment(R.layout.fragment_alternate__brands_) {

    private lateinit var binding: FragmentAlternateBrandsBinding
    var naController: NavController? =null


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

        binding.aBviewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            AlternateBrand_ViewModel::class.java)


    }

}