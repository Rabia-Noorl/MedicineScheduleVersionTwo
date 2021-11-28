package com.example.medicineschedule

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAppointmentsBinding
import com.example.medicineschedule.viewModels.AppointmentViewModel

class AppointmentsActivity : AppCompatActivity() {

    lateinit var viewModel: AppointmentViewModel
    private lateinit var binding: ActivityAppointmentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)
        binding = ActivityAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, AddDoctorActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            AppointmentViewModel::class.java)
        binding.appointmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.appointmentViewModel = viewModel
        }

        viewModel.reminder.observe(this) {reminder ->
            dialogueHandler(reminder)
        }

        viewModel.allRemiders.observe(this){
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
    private fun dialogueHandler(reminderTracker: ReminderTracker){
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        var d : Dialog? = this.let { Dialog(it) }
        d?.setContentView(R.layout.edit_record_dialoge)
        d!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_design);
        d!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        d?.show()

        var name = d?.findViewById<TextView>(R.id.nameEditTV)

        var updateBtn = d?.findViewById<Button>(R.id.updatebutton2)
        var deleteBtn = d?.findViewById<Button>(R.id.deletebutton3)

        var type = d?.findViewById<EditText>(R.id.typeETdialge)
        var status = d?.findViewById<EditText>(R.id.editTextStatusDialog)
        var dateTime = d?.findViewById<EditText>(R.id.editTextTimedialoge)
        var instructions = d?.findViewById<EditText>(R.id.editTextInstructiondialoge)

        name.setText(reminderTracker.names)
        status.setText(reminderTracker.status)
        dateTime.setText(reminderTracker.dateTimes)
        instructions.setText(reminderTracker.instructions)
    }
}