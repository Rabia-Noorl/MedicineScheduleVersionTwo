package com.example.medicineschedule.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.medicineschedule.database.Note
import com.example.medicineschedule.database.NoteDatabase
import com.example.medicineschedule.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val nrepository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init{
        val notesdao = NoteDatabase.getDatabase(application).getNotesDao()
        nrepository= NoteRepository(notesdao)
        allNotes=nrepository.allNotes
    }
    fun deleteNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        nrepository.delete(note)
    }
    fun updateNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        nrepository.update(note)
    }
    fun insertNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        nrepository.insert(note)
    }

}