package com.example.medicineschedule.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderTracker)
    @Delete()
    suspend fun delete(reminder: ReminderTracker)

    @Query(value = "Select * from Reminder_Table order by id ASC")
    fun  getAllRemiders(): LiveData<List<ReminderTracker>>

}