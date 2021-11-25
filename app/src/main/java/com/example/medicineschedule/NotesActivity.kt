package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineschedule.adaptors.NoteClickDeleteInterface
import com.example.medicineschedule.adaptors.NoteClickInterface
import com.example.medicineschedule.adaptors.NotesRVAdapter
import com.example.medicineschedule.database.Note
import com.example.medicineschedule.viewModels.NoteViewModel
import com.getbase.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    lateinit var notesRV:RecyclerView
    lateinit var addFAB: Button
    lateinit var noteViewModel: NoteViewModel
    lateinit var alertdialogbuilder:AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        notesRV=findViewById(R.id.idRVNotes)
        addFAB=findViewById(R.id.idFBAddNote)
        notesRV.layoutManager=LinearLayoutManager(this)
        val noteRVAdapter=NotesRVAdapter(this,this,this)
        notesRV.adapter=noteRVAdapter
        noteViewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
   noteViewModel.allNotes.observe(this, Observer { list->
       list?.let{
           noteRVAdapter.updateList(it)
       }})
        addFAB.setOnClickListener{
            val nIntent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
            startActivity(nIntent)
            this.finish() }}
    override fun onDeleteIconClick(note: Note) {
        alertdialogbuilder=AlertDialog.Builder(this)
        alertdialogbuilder.setTitle("Delete").setIcon(android.R.drawable.ic_delete)
            .setMessage("Do you want to delete?").setCancelable(true).setPositiveButton("Yes"){dialogInterface,it->
                noteViewModel.deleteNote(note)
                Toast.makeText(this,"${note.noteTitle} Deleted",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){dialogInterface,it->
                dialogInterface.cancel()
            }.show()
    }

    override fun onNoteClick(note: Note) {
        val nIntent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
        nIntent.putExtra("noteType","Edit")
        nIntent.putExtra("noteTitle",note.noteTitle)
        nIntent.putExtra("noteDescription",note.noteDescription)
        nIntent.putExtra("noteID",note.id)
        startActivity(nIntent)
        this.finish()

    }

}