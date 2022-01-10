package com.example.medicineschedule.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.fragments.appointments.MoreFragment
import com.example.medicineschedule.fragments.measurement.MedicationFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class updateMeasurement : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context,"from receiver class", Toast.LENGTH_SHORT).show()
        val workBundle: Bundle? = intent!!.getParcelableExtra("bundle")
        val reminderTracker = workBundle!!.getSerializable("reminder") as List<ReminderTracker>?
        intent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        reminderTracker?.forEach { item ->
            context?.let {
                var reminder = ReminderTracker(
                    item.reminderType,
                    item.types,
                    item.names,
                    item.dateTimes,
                    "",
                    item.quantity,
                    item.instructions,
                    item.strenght,
                    item.startDate,
                    item.endDate,
                    item.recodeCreationDate,
                    item.deleteFlage
                )
                var reminderTwo = ReminderTracker(
                    item.reminderType,
                    item.types,
                    item.names,
                    item.dateTimes,
                    "",
                    item.quantity,
                    item.instructions,
                    item.strenght,
                    item.startDate,
                    item.endDate,
                    item.recodeCreationDate,
                    true
                )
                reminderTwo.id = item.id
                CoroutineScope(Dispatchers.IO).launch {
                    ReminderDatabase.getDatabase(context).getReminderDao().insert(reminder)
                    ReminderDatabase.getDatabase(context).getReminderDao().update(reminderTwo)
                }
            }
            if (context != null) {
                MedicationFragment.setAlarm(context)
                MedicationFragment.setAlarm(reminderTracker, context)
            }
        }
    }
}