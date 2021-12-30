package com.example.medicineschedule.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.databinding.FragmentDetailedInfoBinding
import com.example.medicineschedule.viewModels.DetailInfoViewModel

class Detailed_Info_Fragment : Fragment() {

    lateinit var viewModel: DetailInfoViewModel

    private lateinit var binding: FragmentDetailedInfoBinding
    var naController: NavController? =null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            DetailInfoViewModel::class.java)
        binding.viewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.viewModel= viewModel
        }
        viewModel.drugRecord.observe(viewLifecycleOwner){
            it?.let {
                viewModel.ResValue(it)
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner){
            Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
        }

    }

}