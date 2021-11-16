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
    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)
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
//        binding.btndoctorReminder.setOnClickListener {
//            var timetext =  binding.appointmentTime
//            var state  = binding.btndoctorReminder.isChecked
//            timetext.isVisible = state == true
//            if (state){
//                Toast.makeText(this,"Alarm set successfully", Toast.LENGTH_SHORT).show()
//                setAlarm()
//            }
//        }
        binding.btndoctorReminder.setOnClickListener(View.OnClickListener {
            var state  = binding.btndoctorReminder.isChecked
            if(binding.btndoctorReminder.isChecked)
            {binding.appointmentTime.visibility=View.VISIBLE
            }
            else{
                binding.appointmentTime.visibility=View.GONE
            }
//            if (state){
//                Toast.makeText(this,"Alarm set successfully", Toast.LENGTH_SHORT).show()
//                setAlarm()
//            }
        })
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
        binding.appointmentTime.setOnClickListener(View.OnClickListener {

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

        })
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