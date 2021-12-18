package com.example.medicineschedule.fragments.medication

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.AddDose
import com.example.medicineschedule.AddMeasurements
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentMedication2Binding
import com.example.medicineschedule.fragments.home.HomeFragment
import com.example.medicineschedule.viewModels.MeasurmentViewModel
import com.example.medicineschedule.viewModels.MedicineRecViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MedicationFragment : Fragment(R.layout.fragment_medication2) {

    lateinit var viewModel: MeasurmentViewModel

    private lateinit var binding: FragmentMedication2Binding
    lateinit var alertdialogbuilder: AlertDialog.Builder
    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedication2Binding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        binding.addMesButton.setOnClickListener{
            val measurementIntent = Intent(getActivity(), AddMeasurements::class.java)
            startActivity(measurementIntent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
           MeasurmentViewModel::class.java)

        viewModel.reminder.observe(viewLifecycleOwner) { reminder ->
            dialogeBuild(reminder)
        }

        binding.measurmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.measurmentViewModel= viewModel
            viewModel.allRemiders.observe(viewLifecycleOwner){
                it?.let {
                    var recViewList = ArrayList<ReminderTracker>()
                    it.forEach{
                        if(it.reminderType == "mes" && !it.deleteFlage)
                        {
                            recViewList.add(it)
                        }
                    }
                    viewModel.addFun(recViewList)
                    var anim  = binding.measurmentLottieanim
                    var initialText  = binding.initialTV
                    anim.isVisible = recViewList.isEmpty()
                    initialText.isVisible = recViewList.isEmpty()
                }
            }
        }
        return view
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
        var medImg = d?.findViewById<ImageView>(R.id.medImg)

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
        medImg.setImageResource(R.drawable.ic_measurementwhite)

        val calendar = Calendar.getInstance()
        val date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        sdayTV.setText(day)

        if (reminderTracker.reminderType.toString() == "med") {
            // strenghtTV.setText("${reminderTracker.quantity} ${reminderTracker.types}${reminderTracker.strenght}")
            //tyeQuantityV.setText("at ${reminderTracker.dateTimes} ")
        } else if (reminderTracker.reminderType == "mes") {
            tyeQuantityV.setText("Measurement at ${reminderTracker.dateTimes}")
        } else if (reminderTracker.reminderType == "doc") {
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
            val intent = Intent(context, AddMeasurements::class.java)
            intent.putExtra("mes", reminderTracker)
            startActivity(intent)
        }
    }
}