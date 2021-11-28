package com.example.medicineschedule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "Reminder_Table" )
class ReminderTracker(@ColumnInfo(name = "Rem")

                      val reminderType: String,
                      val types:String,
                      val names: String,
                      val dateTimes: String,
                      val status: String,
                      val quantity: String,
                      val instructions: String,
                      val strenght: String,
                      val startDate: String,
                      val endDate: String,
                      val recodeCreationDate: String,
                      val deleteFlage: Boolean) : Serializable
{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}