package com.example.medicineschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class),version = 1,exportSchema = false)

abstract class NoteDatabase:RoomDatabase() {
    abstract fun getNotesDao():NotesDao
    companion object{
        @Volatile
        private var nINSTANCE:NoteDatabase?=null
        fun getDatabase(context: Context):NoteDatabase{
            return nINSTANCE?: synchronized(this){
                val ninstance= Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java
                ,"note_database").build()
                nINSTANCE=ninstance
                ninstance
            }
        }
    }
}