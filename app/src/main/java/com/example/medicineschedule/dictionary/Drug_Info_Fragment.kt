package com.example.medicineschedule.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentDrugInfoBinding
import com.example.medicineschedule.viewModels.AlternateBrand_ViewModel
import com.example.medicineschedule.viewModels.Forms_ViewModel
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.nav_headrer_layout.view.*


class Drug_Info_Fragment : Fragment(R.layout.fragment_drug__info_) {

    private lateinit var binding: FragmentDrugInfoBinding
    var naController: NavController? =null
    lateinit var viewModel: Forms_ViewModel

    private val availableForm = Aavailable_Form_Fragment()
    private val includedDrug = Included_Drugs_Fragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            Forms_ViewModel::class.java)
        binding.formViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.formViewModel= viewModel
        }
        viewModel.drugRecord.observe(viewLifecycleOwner){
            it?.let {
                viewModel.ResValue(it)
            }
        }

        naController =  findNavController()
        binding.availableFormBtn.setOnClickListener {
            //naController?.navigate(R.id.action_aavailable_Form_Fragment_to_included_Drugs_Fragment)
            val fragment = availableForm
            fragmentManager?.beginTransaction()?.replace(R.id.nestedFragment, fragment, fragment.javaClass.getSimpleName())
                ?.commit()

        }
        binding.includedDrugBtn.setOnClickListener {
           // naController?.navigate(R.id.action_included_Drugs_Fragment_to_aavailable_Form_Fragment)
            val fragment = includedDrug
            fragmentManager?.beginTransaction()?.replace(R.id.nestedFragment, fragment, fragment.javaClass.getSimpleName())
                ?.commit()

        }
        binding.alternateeBrandBtn.setOnClickListener {
             naController?.navigate(R.id.action_drug_Info_Fragment_to_alternate_Brands_Fragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrugInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}