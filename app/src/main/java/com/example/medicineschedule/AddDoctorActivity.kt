package com.example.medicineschedule

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

class AddDoctorActivity : AppCompatActivity() {
    var timeFormat= SimpleDateFormat("HH:mm")
    lateinit var binding: ActivityAddDoctorBinding
    lateinit  var viewModel: HomeRecViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            HomeRecViewModel::class.java)

        binding.appointmentTime.setOnClickListener {
            showTimePicker()
        }

        binding.saveTextView.setOnClickListener{
            addDocReminder()

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
        //  var docName = binding..text.toString()
        if ( docName.isNotEmpty() && time.isNotEmpty()){
            var reminder = ReminderTracker("Appointment", "$docName", "$time","", "","","","","", false)
            viewModel.onAddClick(reminder)
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Mandatory fields are missing", Toast.LENGTH_SHORT).show()
        }

    }
}