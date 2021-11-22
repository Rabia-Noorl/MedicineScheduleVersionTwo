package com.example.medicineschedule.fragments.pharmacy

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddDoseBinding

import com.example.medicineschedule.databinding.FragmentPharmacyBinding
import com.example.medicineschedule.viewModels.MedicineRecViewModel
import com.example.medicineschedule.viewModels.ReportViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.fragment_pharmacy.*


class PharmacyFragment : Fragment() {

    lateinit var viewModel: ReportViewModel
    private lateinit var binding: FragmentPharmacyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPharmacyBinding.inflate(inflater, container, false)
        val view = binding.root
        var filterChoice = arrayOf("All Medications","Active Medications")
        var trackChoice = arrayOf("All","Yesterday","Last 7 days","Last 30 days","Last 60 days","Last 90 days","Last 180 days")
        binding.mdlogTrack.setOnClickListener(View.OnClickListener {
            var alertfilterDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
            alertfilterDialog?.setTitle("Select Track Filter")
            alertfilterDialog?.setSingleChoiceItems(
                filterChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.mdlogTrack.setText(filterChoice[position])
                if (position == 0){
                    Toast.makeText(context,"Active", Toast.LENGTH_SHORT).show()
                }
                else if(position == 1)
                {
                    Toast.makeText(context,"All medications", Toast.LENGTH_SHORT).show()
                }
                dialogInterface.dismiss()
            }
            alertfilterDialog?.show()
        })
        binding.imglog.setOnClickListener(View.OnClickListener {
            var alerttrackDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
            alerttrackDialog?.setTitle("Select Filter Options")
            alerttrackDialog?.setSingleChoiceItems(
                trackChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->

                dialogInterface.dismiss()
            }
            alerttrackDialog?.show()
        })

        binding.takeCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(70f)
        }
        binding.missedCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(20f)
        }
        binding.skippedCircularProgressBar.apply {
            this.progressMax = 100f
            this.setProgressWithAnimation(10f)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ReportViewModel::class.java)
        binding.reportViewModek = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.reportViewModek= viewModel
            viewModel.allRemiders.observe(viewLifecycleOwner){
                it?.let {
                    viewModel.addFun(it)
                    getFillters(it)
                    var anim  = binding.reportLottieanim
                    var initialText  = binding.initialTV
                    anim.isVisible = it.isEmpty()
                    initialText.isVisible = it.isEmpty()
                }
            }
        }

    }

    private fun getFillters(list:List<ReminderTracker>){

    }
}