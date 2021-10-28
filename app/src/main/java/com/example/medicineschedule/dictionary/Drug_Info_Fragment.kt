package com.example.medicineschedule.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentDrugInfoBinding
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.nav_headrer_layout.view.*


class Drug_Info_Fragment : Fragment(R.layout.fragment_drug__info_) {

    private lateinit var binding: FragmentDrugInfoBinding
    var naController: NavController? =null

    private val availableForm = Aavailable_Form_Fragment()
    private val includedDrug = Included_Drugs_Fragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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