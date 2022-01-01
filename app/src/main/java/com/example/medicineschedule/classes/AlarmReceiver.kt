package com.example.medicineschedule.classes

import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.medicineschedule.HomeScreen
import com.example.medicineschedule.R
import android.app.Notification
import android.content.Intent.getIntent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.viewModels.HomeRecViewModel
import android.os.Bundle


class AlarmReceiver(): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra("type")
        val name = intent?.getStringExtra("name")
        var i = Intent(context, HomeScreen::class.java)
        if (intent != null) {
            if (type.equals("med")) {
                intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                var pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
                val builder = NotificationCompat.Builder(context!!, "AlarmId")
                    .setSmallIcon(R.drawable.pills)
                    .setContentTitle("Your Daily Medication")
                    .setContentText("$name")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSilent(true)
                    .setContentIntent(pendingIntent)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())
                alarmOnly(context)
                if (context != null) {
                    vibrateFuc(context)
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
                    .setSilent(true)
                    .setContentIntent(pendingIntent)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())
                alarmOnly(context)
                if (context != null) {
                    vibrateFuc(context)
                }
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
                    .setSilent(true)
                    .setContentIntent(pendingIntent)
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())
                alarmOnly(context)
                if (context != null) {
                    vibrateFuc(context)
                }
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