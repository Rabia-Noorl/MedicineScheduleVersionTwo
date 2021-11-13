package com.example.medicineschedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reminder_Table" )
class ReminderTracker(@ColumnInfo(name = "Rem")
           val types: String,
           val names: String,
           val dateTimes: String,
           val status: String,
           val quantity: String,
           val instructions: String
           )
{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}