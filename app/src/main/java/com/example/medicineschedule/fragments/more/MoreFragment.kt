package com.example.medicineschedule.fragments.more

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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.databinding.FragmentMoreBinding
import com.example.medicineschedule.fragments.home.HomeFragment
import com.example.medicineschedule.fragments.medication.MedicationFragment
import com.example.medicineschedule.fragments.pharmacy.PharmacyFragment
import com.example.medicineschedule.viewModels.AppointmentViewModel
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_dictionary.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoreFragment : Fragment() {


    private lateinit var binding: FragmentMoreBinding
    lateinit var viewModel: AppointmentViewModel
    lateinit var alertdialogbuilder: AlertDialog.Builder
    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val intent = Intent(getActivity(), AddDoctorActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            AppointmentViewModel::class.java)
        binding.appointmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.appointmentViewModel = viewModel
        }

        viewModel.reminder.observe(viewLifecycleOwner) {reminder ->
            dialogeBuild(reminder)
        }

        viewModel.allRemiders.observe(viewLifecycleOwner){
            it?.let {
                var recViewList = ArrayList<ReminderTracker>()
                it.forEach{
                    if(it.reminderType == "doc" && !it.deleteFlage)
                    {
                        recViewList.add(it)
                    }
                }
                viewModel.addFun(recViewList)
                var anim  = binding.appoimtmentLottieanim
                var initialText  = binding.initialTV
                anim.isVisible = recViewList.isEmpty()
                initialText.isVisible = recViewList.isEmpty()
            }
        }

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

        val calendar = Calendar.getInstance()
        val date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        sdayTV.setText(day)
        if (reminderTracker.reminderType == "doc") {
            tyeQuantityV.setText("${reminderTracker.dateTimes} ${reminderTracker.status} ")
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
            val intent = Intent(context, AddDoctorActivity::class.java)
            intent.putExtra("doc", reminderTracker)
            startActivity(intent)
        }
    }
}