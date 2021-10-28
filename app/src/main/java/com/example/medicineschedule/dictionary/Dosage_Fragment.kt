package com.example.medicineschedule.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentDetailedInfoBinding

class Dosage_Fragment : Fragment() {

    private lateinit var binding: FragmentDetailedInfoBinding
    var naController: NavController? =null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}