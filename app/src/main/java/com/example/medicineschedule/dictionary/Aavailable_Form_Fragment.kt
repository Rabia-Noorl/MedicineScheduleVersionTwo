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
import com.example.medicineschedule.databinding.FragmentAavailableFormBinding
import com.example.medicineschedule.viewModels.AlternateBrand_ViewModel
import com.example.medicineschedule.viewModels.Forms_ViewModel
import com.example.medicineschedule.viewModels.MyviewModel

class Aavailable_Form_Fragment : Fragment(R.layout.fragment_aavailable__form_) {

    lateinit var viewModel: Forms_ViewModel

    private lateinit var binding: FragmentAavailableFormBinding
    var naController: NavController? =null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAavailableFormBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            Forms_ViewModel::class.java)
        binding.viewModel = viewModel
    }
}