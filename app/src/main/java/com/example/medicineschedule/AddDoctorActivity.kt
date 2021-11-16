package com.example.medicineschedule

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.classes.AlarmReceiver
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddDoctorBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddDoctorActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDoctorBinding
    lateinit  var viewModel: HomeRecViewModel
    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent
    lateinit var picker: MaterialTimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
   //     creatNotificationChannel()

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
                setAlarm()
            }
        }
    }

    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
        Toast.makeText(this,"Alarm set successfully" , Toast.LENGTH_SHORT).show()
    }
    private fun cancelAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent, 0)
        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"Alarm remove successfully" , Toast.LENGTH_SHORT).show()
    }

    private fun showTimePicker() {
         picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Time for Reminder")
            .build()
            picker.show(supportFragmentManager, "ReminderAlarm")

        picker.addOnPositiveButtonClickListener{
            if (picker.hour > 12)
            {
                binding.tvdoseReminder.text = String.format("%02d", picker.hour-12) + ":" + String.format("%02d", picker.minute) + "Pm"

            }else{
                binding.tvdoseReminder.text =  String.format("%02d", picker.hour) + ":" + String.format("%02d", picker.minute) + "AM"
            }
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }

    }
//
//    private fun creatNotificationChannel() {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//        {
//            val name = "ReminderChannel"
//            val description = "Channel for Alarm Manager"
//            val importance  = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel("AlarmId",name,importance)
//            channel.description = description
//            val notificationManager = getSystemService(
//                NotificationManager::class.java
//
//            )
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

    private fun addDocReminder() {
        var docName = binding.medicationName.text.toString()
        //  var docName = binding..text.toString()
        if ( docName.length > 0 ){
            var reminder = ReminderTracker("lady doctor", "$docName", "Appointment at: 5:00mp","", "","")
            viewModel.onAddClick(reminder)
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Mandatory fields are missing", Toast.LENGTH_SHORT).show()
        }

    }
}