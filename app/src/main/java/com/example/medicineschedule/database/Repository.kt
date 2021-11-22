package com.example.medicineschedule.database

import androidx.lifecycle.LiveData

class Repository(private val reminderDao: ReminderDao) {

    val allReminder: LiveData<List<ReminderTracker>> = reminderDao.getAllRemiders()


    suspend fun insert(reminder: ReminderTracker){
        reminderDao.insert(reminder)
    }
    suspend fun delete(reminder: ReminderTracker){
        reminderDao.delete(reminder)
    }

    suspend fun update(reminder: ReminderTracker){
        reminderDao.update(reminder)
    }



}