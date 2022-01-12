package com.example.medicineschedule.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.Z
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.medicineschedule.R
import com.example.medicineschedule.databinding.FragmentIncludedDrugsBinding
import com.example.medicineschedule.viewModels.DetailInfoViewModel
import com.google.errorprone.annotations.Var
import kotlinx.android.synthetic.main.fragment_included__drugs_.*

class Included_Drugs_Fragment : Fragment(R.layout.fragment_included__drugs_) {

    lateinit var viewModel: DetailInfoViewModel
    var isRotateone:Boolean = false
    var isRotatetwo:Boolean = false
    var isRotatethree:Boolean = false
    var isRotatefour:Boolean = false
    var isRotatefive:Boolean = false
    var isRotatesix:Boolean = false

    private lateinit var binding: FragmentIncludedDrugsBinding
    var naController: NavController? =null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naController =  findNavController()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            DetailInfoViewModel::class.java)
        binding.includeDrigViewmodel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.includeDrigViewmodel= viewModel
        }

        binding.constraintLayout.setOnClickListener {
            if (!isRotateone)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView3.startAnimation(animationRotate)
                isRotateone = true
                binding.howItsWorkTV.isVisible = true
                binding.view7.isVisible = true

            }else if(isRotateone)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView3.startAnimation(animationRotate)
                isRotateone = false
                binding.howItsWorkTV.isVisible = false
                binding.view7.isVisible = false

            }
        }
        binding.constraintLayout5.setOnClickListener {
            if (!isRotatetwo)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView4.startAnimation(animationRotate)
                isRotatetwo = true
                binding.medInterectionsTV.isVisible = true
                binding.view8.isVisible = true

            }else if(isRotatetwo)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView4.startAnimation(animationRotate)
                isRotatetwo = false
                binding.medInterectionsTV.isVisible = false
                binding.view8.isVisible = false

            }
        }
        binding.constraintLayout6.setOnClickListener {
            if (!isRotatethree)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView5.startAnimation(animationRotate)
                isRotatethree = true
                binding.sideEffectTV.isVisible = true
                binding.view9.isVisible = true

            }else if(isRotatethree)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView5.startAnimation(animationRotate)
                isRotatethree = false
                binding.sideEffectTV.isVisible = false
                binding.view9.isVisible = false

            }
        }
        binding.constraintLayout7.setOnClickListener {
            if (!isRotatefour)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView6.startAnimation(animationRotate)
                isRotatefour = true
                binding.prescribedForTV.isVisible = true
                binding.view10.isVisible = true

            }else if(isRotatefour)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView6.startAnimation(animationRotate)
                isRotatefour = false
                binding.prescribedForTV.isVisible = false
                binding.view10.isVisible = false

            }
        }
        binding.constraintLayout8.setOnClickListener {
            if (!isRotatefive)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView7.startAnimation(animationRotate)
                isRotatefive = true
                binding.warningsTV.isVisible = true
                binding.view11.isVisible = true

            }else if(isRotatefive)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView7.startAnimation(animationRotate)
                isRotatefive = false
                binding.warningsTV.isVisible = false
                binding.view11.isVisible = false

            }
        }
        binding.doseLyout.setOnClickListener {
            if (!isRotatesix)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
                animationRotate.fillAfter = true
                imageView8.startAnimation(animationRotate)
                isRotatesix = true
                binding.missedDoseTV.isVisible = true
                binding.overDoseTV.isVisible = true
                binding.view12.isVisible = true

            }else if(isRotatesix)
            {
                val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate_back)
                animationRotate.fillAfter = true
                imageView8.startAnimation(animationRotate)
                isRotatesix = false
                binding.missedDoseTV.isVisible = false
                binding.overDoseTV.isVisible = false
                binding.view12.isVisible = false

            }
        }

        viewModel.drugRecord.observe(viewLifecycleOwner){
            it?.let {
               // viewModel.ResValue(it)

                it.forEach{
                    if (it.HowItsWork.equals("-") || it.HowItsWork.equals("----") || it.HowItsWork.equals("-----") || it.HowItsWork.equals("")){
                        binding.constraintLayout.isVisible = false
                    }else{
                        binding.howItsWorkTV.setText(it.HowItsWork)
                    }
                    if (it.prescribedFor.equals("-") || it.prescribedFor.equals("----") || it.prescribedFor.equals("-----") || it.prescribedFor.equals("")){
                        binding.constraintLayout8.isVisible = false
                    }else{
                        val str = AddSpacesToSentence(it.prescribedFor.toString())
                        binding.medInterectionsTV.setText(str)
                    }
                    if (it.sideEffects.equals("-") || it.sideEffects.equals("----") || it.sideEffects.equals("-----") || it.sideEffects.equals("")){
                        binding.constraintLayout6.isVisible = false
                    }else{
                        val str = AddSpacesToSentence(it.sideEffects.toString())
                        binding.sideEffectTV.setText(str)
                    }
                    if (it.warrnings.equals("-") || it.warrnings.equals("----") || it.warrnings.equals("-----") || it.warrnings.equals("")){
                        binding.constraintLayout7.isVisible = false
                    }else{
                        binding.prescribedForTV.setText(it.warrnings)
                    }

                    if (it.medIntrerection.equals("-") || it.medIntrerection.equals("----") || it.medIntrerection.equals("-----") || it.medIntrerection.equals("")){
                        binding.constraintLayout5.isVisible = false
                    }else{
                        binding.warningsTV.setText(it.medIntrerection)
                    }

                    if (it.missedDose.equals("-") || it.missedDose.equals("----") || it.missedDose.equals("-----") || it.missedDose.equals("")){
                        binding.doseLyout.isVisible = false
                    }else{
                        binding.missedDoseTV.setText(it.missedDose)
                    }
                    if (it.overdose.equals("-") || it.overdose.equals("----") || it.overdose.equals("-----") || it.overdose.equals("")){
                        binding.doseLyout.isVisible = false
                    }else{
                        binding.overDoseTV.setText(it.overdose)
                    }

                    binding.dataNotAvailableTV.isVisible = !binding.constraintLayout.isVisible && !binding.constraintLayout5.isVisible && !binding.constraintLayout6.isVisible && !binding.constraintLayout7.isVisible && !binding.constraintLayout8.isVisible && !binding.doseLyout.isVisible
                }
            }
        }
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

    fun rotat(view: View){
        view.setOnClickListener {
            val animationRotate = AnimationUtils.loadAnimation(context, R.anim.rotate)
            it.startAnimation(animationRotate)
        }
    }

    fun AddSpacesToSentence(string: String):String
    {
        val text = string
        val cleanText = text.replace("\\d+".toRegex(), "").replace("(.)([A-Z])".toRegex(), "$1 \r\n$2")
        return cleanText
    }

}