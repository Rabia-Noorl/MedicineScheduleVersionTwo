package com.example.medicineschedule.fragments.report

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.whenCreated
import com.example.medicineschedule.*
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentPharmacyBinding
import com.example.medicineschedule.fragments.medicines.HomeFragment
import com.example.medicineschedule.viewModels.ReportViewModel
import kotlinx.android.synthetic.main.dialog_frag_layout.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
class PharmacyFragment : Fragment() {

    lateinit var viewModel: ReportViewModel
    lateinit var alertdialogbuilder: AlertDialog.Builder
    private lateinit var binding: FragmentPharmacyBinding
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
    var index=0

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
               index
            ) { dialogInterface: DialogInterface, position: Int ->
                index=position
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

        viewModel.reminder.observe(viewLifecycleOwner) { reminder ->
            dialogeBuild(reminder)
        }
        binding.reportViewModek = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.reportViewModek= viewModel
            mrepdays.observe(viewLifecycleOwner) {
                var int = it?.get(0)
               // Toast.makeText(context, "$int", Toast.LENGTH_SHORT).show()
                viewModel.allRemiders.observe(viewLifecycleOwner) {
                    it?.let {
                        int?.let { it1 ->
                            getFillters(it, it1) }?.let { it2 ->
                            viewModel.addFun(it2)
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

                                binding.takenPercentTv.setText("${takenList.size} OUT OF ${it.size}")
                                binding.missedPercentTV.setText("${missedList.size} OUT OF ${it.size}")
                                binding.skippedPercentTV.setText("${skippedList.size} OUT OF ${it.size}")

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

                                binding.takenPercentTv.setText("${takenList.size} OUT OF ${it.size}")
                                binding.missedPercentTV.setText("${missedList.size} OUT OF ${it.size}")
                                binding.skippedPercentTV.setText("${skippedList.size} OUT OF ${it.size}")

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
        cal.add(Calendar.DAY_OF_YEAR, -int)
        list.forEach{
            val temp = it.startDate
            try {
                var date = formatter.parse(temp)
                if(date.after(cal.time))
                {
                    allRemiders.add(it)
                   // Toast.makeText(context,"$int  $date is after ${cal.time}  ", Toast.LENGTH_LONG).show()
                }else if(date.before(cal.time))
                {
                   //  Toast.makeText(context,"$int $date is befor  ${cal.time}  ", Toast.LENGTH_LONG).show()
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return allRemiders
    }

    @SuppressLint("ResourceAsColor")
    private fun dialogeBuild(reminderTracker: ReminderTracker) {
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        var d: Dialog? = context?.let { Dialog(it) }
        d?.setContentView(R.layout.dialog_frag_layout)
        d!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_design);
        d!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        d?.show()

        var unTakeBtn = d?.findViewById<TextView>(R.id.unTakeBtn)
        var takeBtn = d?.findViewById<TextView>(R.id.takeBtn)
        var reschedualBtn = d?.findViewById<TextView>(R.id.reschedualBtn)
        var tyeQuantityV = d?.findViewById<TextView>(R.id.tyeQuantityV)


        var deletBtn = d?.findViewById<ImageView>(R.id.deleteImg)
        var editBtn = d?.findViewById<ImageView>(R.id.editImg)
        var cancelBtn = d?.findViewById<ImageView>(R.id.cancelBtn)
        var imgeView = d?.findViewById<ImageView>(R.id.medImg)

        var nameTv = d?.findViewById<TextView>(R.id.nameTV)
        var statusTV = d?.findViewById<TextView>(R.id.statusTV)

        var sTimeTV = d?.findViewById<TextView>(R.id.sTimeTV)
        var sdayTV = d?.findViewById<TextView>(R.id.sdayTV)
        var strenghtTV = d?.findViewById<TextView>(R.id.strenghtTV)
        var infoTimeTV = d?.findViewById<TextView>(R.id.infoTimeTV)
        var editImg = d?.findViewById<ImageView>(R.id.editImg)

        nameTv.setText(reminderTracker.names)
        sTimeTV.setText(reminderTracker.dateTimes)
        statusTV.setText(reminderTracker.status)
        strenghtTV.setText("${reminderTracker.quantity} ${reminderTracker.types}${reminderTracker.strenght}")
        tyeQuantityV.setText("at ${reminderTracker.dateTimes} ")

        val calendar = Calendar.getInstance()
        val date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        sdayTV.setText(day)

        if (reminderTracker.reminderType.toString() == "med") {
             strenghtTV.setText("${reminderTracker.quantity} ${reminderTracker.types}${reminderTracker.strenght}")
            tyeQuantityV.setText("at ${reminderTracker.dateTimes} ")
          //  medImg.setImageResource(R.drawable.pills)
        } else if (reminderTracker.reminderType == "mes") {
            tyeQuantityV.setText("Measurement at ${reminderTracker.dateTimes}")
        //    medImg.setImageResource(R.drawable.ic_measurementwhite)
        } else if (reminderTracker.reminderType == "doc") {
          //  medImg.setImageResource(R.drawable.doctor)
            tyeQuantityV.setText("Apointment at ${reminderTracker.dateTimes} ${reminderTracker.status} ")
        }

        unTakeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#f98365"))
            takeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!HomeFragment.statusFlag) {
                unTakeBtn.setText("TAKE")
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    reminderTracker.types,
                    reminderTracker.names,
                    reminderTracker.dateTimes,
                    "",
                    reminderTracker.quantity,
                    reminderTracker.instructions,
                    reminderTracker.strenght,
                    reminderTracker.startDate,
                    reminderTracker.endDate,
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = true
                statusTV.setText("${rem.status}")
                return@setOnClickListener
            } else if (HomeFragment.statusFlag) {
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    "${reminderTracker.types}",
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Taken",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                //statusTV.setTextColor(R.color.doneColor)
                statusTV.setTextColor(getResources().getColor(R.color.doneColor, null))

                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = false
                statusTV.setText("${rem.status}")
                unTakeBtn.setText("UN_TAKE")
                return@setOnClickListener
            }
        }

        takeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            takeBtn.setTextColor(Color.parseColor("#f98365"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!HomeFragment.statusFlag) {
                val rem = ReminderTracker(
                    reminderTracker.reminderType,
                    reminderTracker.types,
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Missed",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = true
                statusTV.setText("${rem.status}")
                takeBtn.setText("SKIPPED")
                statusTV.setTextColor(getResources().getColor(R.color.errorColor, null))
                return@setOnClickListener
            } else if (HomeFragment.statusFlag) {
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    "${reminderTracker.types}",
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Skipped",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = false
                statusTV.setText("${rem.status}")
                takeBtn.setText("MISSED")
                statusTV.setTextColor(getResources().getColor(R.color.greyColr, null))
                return@setOnClickListener
            }
        }
        reschedualBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            takeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            reschedualBtn.setTextColor(Color.parseColor("#f98365"))

            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(reminderTracker.dateTimes.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker =
                TimePickerDialog(
                    context, { view, hourOfDay, minute ->
                        var selectedTime = Calendar.getInstance()
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedTime.set(Calendar.MINUTE, minute)
                        var time = timeFormat.format(selectedTime.time)
                        var rem = ReminderTracker(
                            reminderTracker.reminderType,
                            reminderTracker.types,
                            reminderTracker.names,
                            time,
                            "",
                            reminderTracker.quantity,
                            reminderTracker.instructions,
                            reminderTracker.strenght,
                            reminderTracker.startDate,
                            reminderTracker.endDate,
                            reminderTracker.recodeCreationDate,
                            reminderTracker.deleteFlage
                        )
                        rem.id = reminderTracker.id
                        viewModel.onEditClick(rem)
                        sTimeTV.setText(time)
                        tyeQuantityV.setText("at $time")
                    },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                )
            timePicker.show()
        }

        deletBtn.setOnClickListener {
            alertdialogbuilder = AlertDialog.Builder(requireActivity())
            alertdialogbuilder.setCancelable(false)
            alertdialogbuilder.setTitle("Delete").setIcon(R.drawable.ic_delete)
                .setMessage("Are you sure you want to Delete it?").setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
                    var rem = ReminderTracker(
                        reminderTracker.reminderType,
                        reminderTracker.types,
                        reminderTracker.names,
                        reminderTracker.dateTimes,
                        reminderTracker.status,
                        reminderTracker.quantity,
                        reminderTracker.instructions,
                        reminderTracker.strenght,
                        reminderTracker.startDate,
                        reminderTracker.endDate,
                        reminderTracker.recodeCreationDate,
                        true
                    )
                    rem.id = reminderTracker.id
                    viewModel.onEditClick(rem)
                    viewModel.deletDrug(rem)
                    d?.cancel()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()
            editBtn.setOnClickListener {
                val medicineIntent = Intent(getActivity(), AddDose::class.java)
                startActivity(medicineIntent)
            }
        }
        editImg.setOnClickListener {
            d?.cancel()
            if (reminderTracker.reminderType == "med") {
                val intent = Intent(context, AddDose::class.java)
                intent.putExtra("med", reminderTracker)
                startActivity(intent)
            } else if (reminderTracker.reminderType == "mes") {
                val intent = Intent(context, AddMeasurements::class.java)
                intent.putExtra("mes", reminderTracker)
                startActivity(intent)
            } else if (reminderTracker.reminderType == "doc") {
                val intent = Intent(context, AddDoctorActivity::class.java)
                intent.putExtra("doc", reminderTracker)
                startActivity(intent)
            }
        }
        cancelBtn.setOnClickListener{
            d?.cancel()
        }

    }
}