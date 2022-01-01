package com.example.medicineschedule.classes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.getSystemService
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.fragments.medicines.HomeFragment
import com.example.medicineschedule.viewModels.HomeRecViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class updatRVreceiver : BroadcastReceiver() {

    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent

    override fun onReceive(context: Context?, intent: Intent?) {

        val workBundle: Bundle? = intent!!.getParcelableExtra("bundle")
        val reminderTracker = workBundle!!.getSerializable("reminder") as List<ReminderTracker>?
        intent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        reminderTracker?.forEach { item ->
            CoroutineScope(Dispatchers.IO).launch {
                setAlarm(reminderTracker, context)
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

    suspend fun setAlarm(list: List<ReminderTracker>, context: Context?) {
        list.forEach() {
            var str = it.dateTimes.toString()
            val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            calendar = Calendar.getInstance()
            calendar.time = sdf.parse(str)
            calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
            calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
            calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            calendar[Calendar.HOUR_OF_DAY] = calendar.time.hours
            calendar[Calendar.MINUTE] = calendar.time.minutes
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val date = Date()
            if (calendar.time.after(date)) {
                alarmManager =
                    context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.putExtra("type", "med")
                intent.putExtra("name", "You have ${it.names} ${it.types} to take!")
                pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            else if (calendar.time.before(date)) {
                if (it.status == "") {

                    var reminder = ReminderTracker(
                        "${it.reminderType}",
                        "${it.types}",
                        "${it.names}",
                        "${it.dateTimes}",
                        "Taken", "${it.quantity}",
                        "${it.instructions}",
                        "${it.strenght}",
                        "${it.startDate}",
                        "${it.endDate}",
                        "${it.recodeCreationDate}",
                        it.deleteFlage
                    )
                    reminder.id = it.id
                    if (context != null) {
                        ReminderDatabase.getDatabase(context).getReminderDao().update(reminder)
                    }
                }
            }

        }
    }
    private fun cancelAlarm(id:Int, context: Context?) {
            alarmManager =
                context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context, "Alarm remove successfully", Toast.LENGTH_SHORT).show()
    }
}