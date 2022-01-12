package com.example.medicineschedule.classes

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.medicineschedule.HomeScreen
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.fragments.appointments.MoreFragment
import com.example.medicineschedule.fragments.measurement.MedicationFragment
import com.example.medicineschedule.fragments.medicines.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

import android.app.Notification





class AlarmReceiver(): BroadcastReceiver() {

    var soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val longArray  = longArrayOf(1000L, 2000L, 3000L)
    override fun onReceive(context: Context?, intent: Intent?) {
        val GROUP_KEY_WORK_EMAIL = "com.example.medicineschedule"
        val bundle: Bundle? = intent!!.getParcelableExtra("bundle")
        val item = bundle!!.getSerializable("item") as ReminderTracker
        val type = bundle!!.getString("type")
        val name = bundle!!.getString("name")
        var i = Intent(context, HomeScreen::class.java)
        if (intent != null) {
            if (type.equals("med")) {
                Toast.makeText(context, "${item.names}", Toast.LENGTH_SHORT).show()
                intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

                val builder:Notification = NotificationCompat.Builder(context!!, "AlarmId")
                    .setSmallIcon(com.example.medicineschedule.R.drawable.pills)
                    .setContentTitle("Your Daily Medication")
                    .setContentText("$name")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setVibrate(longArray)
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .setGroupSummary(true)
                    // .setSilent(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .build()
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(Random().nextInt(), builder)
                if (item.status == "") {
                    var reminder = ReminderTracker(
                        "${item.reminderType}",
                        "${item.types}",
                        "${item.names}",
                        "${item.dateTimes}",
                        "Taken", "${item.quantity}",
                        "${item.instructions}",
                        "${item.strenght}",
                        "${item.startDate}",
                        "${item.endDate}",
                        "${item.recodeCreationDate}",
                        item.deleteFlage
                    )
                    reminder.id = item.id
                    CoroutineScope(Dispatchers.IO).launch {
                        ReminderDatabase.getDatabase(context).getReminderDao().update(reminder)
                    }
 //                   alarmOnly(context)
//                    if (context != null) {
//                        vibrateFuc(context)
//                    }
                    HomeFragment.setAlarm(item, context)
                }

            } else if (type.equals("doc")) {
                intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
                val builder = NotificationCompat.Builder(context!!, "AlarmId")
                    .setSmallIcon(R.drawable.doctor)
                    .setContentTitle("Your Daily Medication")
                    .setContentText("$name")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                   // .setSilent(true)
                    .setSound(soundUri)
                    .setVibrate(longArray)
                    .setContentIntent(pendingIntent)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())
                if (item.status == "") {
                    var reminder = ReminderTracker(
                        "${item.reminderType}",
                        "${item.types}",
                        "${item.names}",
                        "${item.dateTimes}",
                        "Taken", "${item.quantity}",
                        "${item.instructions}",
                        "${item.strenght}",
                        "${item.startDate}",
                        "${item.endDate}",
                        "${item.recodeCreationDate}",
                        item.deleteFlage
                    )
                    reminder.id = item.id
                    CoroutineScope(Dispatchers.IO).launch {
                        ReminderDatabase.getDatabase(context).getReminderDao().update(reminder)
                    }
                }

              //  alarmOnly(context)
//                if (context != null) {
//                    vibrateFuc(context)
//                }
                MoreFragment.setAlarm(item, context)
            } else if (type == "mes"){
                intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
                val builder = NotificationCompat.Builder(context!!, "AlarmId")
                    .setSmallIcon(R.drawable.ic_addmeasurement)
                    .setContentTitle("Your Daily Medication")
                    .setContentText("$name")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                   // .setSilent(true)
                    .setSound(soundUri)
                    .setVibrate(longArray)
                    .setContentIntent(pendingIntent)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())

                if (item.status == "") {
                    var reminder = ReminderTracker(
                        "${item.reminderType}",
                        "${item.types}",
                        "${item.names}",
                        "${item.dateTimes}",
                        "Taken", "${item.quantity}",
                        "${item.instructions}",
                        "${item.strenght}",
                        "${item.startDate}",
                        "${item.endDate}",
                        "${item.recodeCreationDate}",
                        item.deleteFlage
                    )
                    reminder.id = item.id
                    CoroutineScope(Dispatchers.IO).launch {
                        ReminderDatabase.getDatabase(context).getReminderDao().update(reminder)
                    }
                }
              //  alarmOnly(context)
//                if (context != null) {
//                    vibrateFuc(context)
//                }
                MedicationFragment.setAlarm(item, context)
            }
        }
    }
  }
    fun alarmOnly(context: Context?){
        var mediaPlayer = MediaPlayer.create(context, com.example.medicineschedule.R.raw.shakesound)
        mediaPlayer.start()
    }

    fun vibrateFuc(context:Context)
    {
        val vibrator = context.getSystemService<Vibrator>()
        vibrator?.let {
            if (vibrator.hasVibrator()) { // Vibrator availability checking
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
                } else {
                    vibrator.vibrate(500) // Vibrate method for below API Level 26
                }

            }
        }
    }