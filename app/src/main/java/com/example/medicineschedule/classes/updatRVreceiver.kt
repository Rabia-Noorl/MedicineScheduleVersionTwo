package com.example.medicineschedule.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class updatRVreceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val workBundle: Bundle? = intent!!.getParcelableExtra("bundle")
        val reminderTracker = workBundle!!.getSerializable("reminder") as List<ReminderTracker>?
        intent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        reminderTracker?.forEach { item ->
            CoroutineScope(Dispatchers.IO).launch {
                context?.let {
                    var reminder = ReminderTracker(
                        item.reminderType,
                        item.types,
                        item.names,
                        item.dateTimes,
                        " ",
                        item.quantity,
                        item.instructions,
                        item.strenght,
                        item.startDate,
                        item.endDate,
                        item.recodeCreationDate,
                        item.deleteFlage
                    )

                    ReminderDatabase.getDatabase(context).getReminderDao().insert(reminder)
                    var reminderTwo = ReminderTracker(
                        item.reminderType,
                        item.types,
                        item.names,
                        item.dateTimes,
                        " ",
                        item.quantity,
                        item.instructions,
                        item.strenght,
                        item.startDate,
                        item.endDate,
                        item.recodeCreationDate,
                        true
                    )
                    reminderTwo.id = item.id
                    ReminderDatabase.getDatabase(context).getReminderDao().update(reminderTwo)
                }
            }
            alarmOnly(context)
            if (context != null) {
                vibrateFuc(context)
            }
        }
    }

    fun alarmOnly(context: Context?) {
        var mediaPlayer = MediaPlayer.create(context, com.example.medicineschedule.R.raw.shakesound)
        mediaPlayer.start()
    }

    fun vibrateFuc(context: Context) {
        val vibrator = context.getSystemService<Vibrator>()
        vibrator?.let {
            if (vibrator.hasVibrator()) { // Vibrator availability checking
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    ) // New vibrate method for API Level 26 or higher
                } else {
                    vibrator.vibrate(500) // Vibrate method for below API Level 26
                }

            }
        }
    }
}