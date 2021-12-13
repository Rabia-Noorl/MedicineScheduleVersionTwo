package com.example.medicineschedule.classes

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.medicineschedule.HomeScreen
import com.example.medicineschedule.R

class AlarmReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       // alarmOnly(context)
        var i = Intent(context, HomeScreen::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        var pendingIntent = PendingIntent.getActivity(context,0,i,0)
        val builder = NotificationCompat.Builder(context!!, "AlarmId")
            .setSmallIcon(R.drawable.pills)
            .setContentTitle("Your Daily Medication")
            .setContentText("You have prescriptions to take!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

    }
    fun alarmOnly(context: Context?){
        var alarmUri =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        var ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()
    }

}