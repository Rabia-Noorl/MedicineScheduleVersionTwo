package com.example.medicineschedule

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.classes.AlarmReceiver
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddDoctorBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class AddDoctorActivity : AppCompatActivity(){

    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)
    lateinit var binding: ActivityAddDoctorBinding
    lateinit  var viewModel: HomeRecViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val record: ReminderTracker? = intent.getSerializableExtra("rem") as ReminderTracker?
        binding.medicationName.setText(record?.names)
        binding.docTypeTV.setText(record?.instructions)
        binding.appointmentTime.setText(record?.dateTimes)

        //create speciality dropdown
        var speciality=arrayOf("Anesthesiologist","Child Specialist","Dermatologist","ENT Specialist","Gynecologist","Neurologist","Ophthalmologist","Pathologist","Psychiatrist","Radiation Oncologist","Urologist","Other")
        binding.docTypeTV.setOnClickListener(View.OnClickListener {
            var alertDialogDocType = AlertDialog.Builder(this)
            alertDialogDocType.setTitle("Select Speciality")
            alertDialogDocType.setSingleChoiceItems(
                speciality,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.docTypeTV.setText(speciality[position])
                if(position==11)
                {
                    binding.customDocType.visibility=View.VISIBLE
                }
                else{
                    binding.customDocType.visibility=View.GONE
                }
                dialogInterface.dismiss()

            }
            alertDialogDocType.show()
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            HomeRecViewModel::class.java)

        binding.appointmentTime.setOnClickListener {
            showTimePicker()
        }

        binding.saveTextView.setOnClickListener{
            if(record == null){

                addDocReminder()

        }else{
                var docName = binding.medicationName.text.toString()
                var time = binding.appointmentTime.text
                var type = binding.docTypeTV.text
                if ( docName.isNotEmpty() && time.isNotEmpty()){
                    var reminder = ReminderTracker("doc","Appointment:", "$docName", "$time","", "","$type","","","", "${Calendar.getInstance().time}",false)
                   reminder.id = record.id
                    viewModel.onEditClick(reminder)
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Mandatory fields are missing", Toast.LENGTH_SHORT).show()
                }

        }
        }
        binding.backArrow.setOnClickListener{
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        binding.btndoctorReminder.setOnClickListener {
            var timetext =  binding.appointmentTime
            var state  = binding.btndoctorReminder.isChecked
            timetext.isVisible = state == true
            if (state){
                Toast.makeText(this,"Alarm set successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePicker() {
        var calendar= Calendar.getInstance()
        try{
            var date=timeFormat.parse(binding.appointmentTime.text.toString())
            calendar.time=date}
        catch (e:Exception){
            e.printStackTrace()
        }
        var timePicker=
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.appointmentTime.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

        timePicker.show()
    }
private fun addDocReminder() {
    var docName = binding.medicationName.text.toString()
    var time = binding.appointmentTime.text
    var type = binding.docTypeTV.text

    //  var docName = binding..text.toString()
    if ( docName.isNotEmpty() && time.isNotEmpty()){
        var reminder = ReminderTracker("doc","Appointment:", "$docName", "$time","", "","$type","","","", "${Calendar.getInstance().time}",false)
        viewModel.onAddClick(reminder)
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }else{
        Toast.makeText(this, "Mandatory fields are missing", Toast.LENGTH_SHORT).show()
    }

}

}