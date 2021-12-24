package com.example.medicineschedule.fragments.report

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentPharmacyBinding
import com.example.medicineschedule.viewModels.ReportViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
class PharmacyFragment : Fragment() {

    lateinit var viewModel: ReportViewModel
    private lateinit var binding: FragmentPharmacyBinding

    companion object{
        var mrepdays: MutableLiveData<List<Int>> = MutableLiveData<List<Int>>()
        var reportFilters: MutableLiveData<List<Int>> = MutableLiveData<List<Int>>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPharmacyBinding.inflate(inflater, container, false)
        val view = binding.root
//        var filterChoice = arrayOf("All Medications","Active Medications")
        var trackChoice = arrayOf( "Clear log","All","Yesterday","Last 7 days","Last 30 days","Last 60 days","Last 90 days","Last 180 days")
        mrepdays.postValue(listOf(0))
        reportFilters.postValue(listOf(0))

//        binding.mdlogTrack.setOnClickListener(View.OnClickListener {
//            var alertfilterDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
//            alertfilterDialog?.setTitle("Select Track Filter")
//            alertfilterDialog?.setSingleChoiceItems(
//                filterChoice,
//                0
//            ) { dialogInterface: DialogInterface, position: Int ->
//                binding.mdlogTrack.setText(filterChoice[position])
//                if (position == 0){
//                    PharmacyFragment.reportFilters.postValue(listOf(0))
//                }
//                else if(position == 1)
//                {
//                    PharmacyFragment.reportFilters.postValue(listOf(1))
//                }
//                dialogInterface.dismiss()
//            }
//            alertfilterDialog?.show()
//        })
        binding.mdlogTrack.setOnClickListener(View.OnClickListener {
            var alerttrackDialog = getActivity()?.let { it1 -> AlertDialog.Builder(it1) }
            alerttrackDialog?.setTitle("Select Filter Options")
            alerttrackDialog?.setSingleChoiceItems(
                trackChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                if (position == 0){
                    PharmacyFragment.mrepdays.postValue(listOf(0))
                }else if(position ==1)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(10))
                }else if(position ==2)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(1))
                }else if(position ==3)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(7))
                }else if(position ==4)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(30))
                }else if(position ==5)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(60))
                }else if(position ==6)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(90))
                }else if(position ==7)
                {
                    PharmacyFragment.mrepdays.postValue(listOf(180))
                }
                dialogInterface.dismiss()
            }
            alerttrackDialog?.show()
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var anim  = binding.reportLottieanim
        var initialText  = binding.initialTV

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ReportViewModel::class.java)

        binding.reportViewModek = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.reportViewModek= viewModel
            mrepdays.observe(viewLifecycleOwner) {
                var int = it?.get(0)
                viewModel.allRemiders.observe(viewLifecycleOwner) {
                    it?.let {
                        int?.let { it1 -> getFillters(it, it1) }?.let { it2 -> viewModel.addFun(it2)
                            if (int == 0) {
                                anim.isVisible = true
                                initialText.isVisible = true

                                binding.takenPercentTv.setText("")
                                binding.missedPercentTV.setText("")
                                binding.skippedPercentTV.setText("")

                                binding.takeCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(0.0f)
                                }
                                binding.missedCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(0.0f)
                                }
                                binding.skippedCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(0.0f)
                                }

                            } else if (int == 10) {
                                viewModel.addFun(it)
                                anim.isVisible = false
                                initialText.isVisible = false
                                val takenList = ArrayList<ReminderTracker>()
                                val missedList = ArrayList<ReminderTracker>()
                                val skippedList = ArrayList<ReminderTracker>()
                                it.forEach()
                                {
                                    if (it.status.toString() == "Taken") {
                                        takenList.add(it)
                                    } else if (it.status.toString() == "Skipped") {
                                        skippedList.add(it)

                                    } else if (it.status.toString() == "Missed") {
                                        missedList.add(it)
                                    }
                                }

                                var percentTaken =
                                    ((takenList.size / it.size.toFloat()) * 100).toInt()
                                var percentMissed =
                                    ((missedList.size / it.size.toFloat()) * 100).toInt()
                                var percentSkipped =
                                    ((skippedList.size / it.size.toFloat()) * 100).toInt()

                                binding.takenPercentTv.setText("${takenList.size}/${it.size}")
                                binding.missedPercentTV.setText("${missedList.size}/${it.size}")
                                binding.skippedPercentTV.setText("${skippedList.size}/${it.size}")

                                binding.takeCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(takenList.size.toFloat())
                                }
                                binding.missedCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(missedList.size.toFloat())
                                }
                                binding.skippedCircularProgressBar.apply {
                                    this.progressMax = it.size.toFloat()
                                    this.setProgressWithAnimation(skippedList.size.toFloat())
                                }
                            } else {
                                anim.isVisible = false
                                initialText.isVisible = false
                                val takenList = ArrayList<ReminderTracker>()
                                val missedList = ArrayList<ReminderTracker>()
                                val skippedList = ArrayList<ReminderTracker>()
                                it2.forEach()
                                {
                                    if (it.status.toString() == "Taken") {
                                        takenList.add(it)
                                    } else if (it.status.toString() == "Skipped") {
                                        skippedList.add(it)

                                    } else if (it.status.toString() == "Missed") {
                                        missedList.add(it)
                                    }
                                }

                                var percentTaken =
                                    ((takenList.size / it2.size.toFloat()) * 100).toInt()
                                var percentMissed =
                                    ((missedList.size / it2.size.toFloat()) * 100).toInt()
                                var percentSkipped =
                                    ((skippedList.size / it2.size.toFloat()) * 100).toInt()

                                binding.takenPercentTv.setText(percentTaken.toString() + "% Taken")
                                binding.missedPercentTV.setText(percentMissed.toString() + "% Missed")
                                binding.skippedPercentTV.setText(percentSkipped.toString() + "% Skipped")

                                binding.takeCircularProgressBar.apply {
                                    this.progressMax = it2.size.toFloat()
                                    this.setProgressWithAnimation(takenList.size.toFloat())
                                }
                                binding.missedCircularProgressBar.apply {
                                    this.progressMax = it2.size.toFloat()
                                    this.setProgressWithAnimation(missedList.size.toFloat())
                                }
                                binding.skippedCircularProgressBar.apply {
                                    this.progressMax = it2.size.toFloat()
                                    this.setProgressWithAnimation(skippedList.size.toFloat())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFillters(list:List<ReminderTracker>,int:Int): List<ReminderTracker>{
        val allRemiders: MutableList<ReminderTracker> = ArrayList<ReminderTracker>()
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
        var cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -int)
        list.forEach{
            val temp = it.recodeCreationDate
            try {
                var date = formatter.parse(temp)
                if(date.after(cal.time))
                {
                    allRemiders.add(it)
                    // Toast.makeText(context,"$int  $date is after ${cal.time}  ", Toast.LENGTH_LONG).show()
                }else if(date.before(cal.time))
                {
                    // Toast.makeText(context,"$int $date is befor  ${cal.time}  ", Toast.LENGTH_LONG).show()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return allRemiders
    }
}