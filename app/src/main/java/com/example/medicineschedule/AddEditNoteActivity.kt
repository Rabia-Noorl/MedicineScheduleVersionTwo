package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.Note
import com.example.medicineschedule.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.activity_notes.*
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt:EditText
    lateinit var noteDescriptionEdt:EditText
    lateinit var addUpdateBtn:Button
    lateinit var nviewModel:NoteViewModel
    var noteID= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdt=findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdt=findViewById(R.id.idEdtNoteDescription)
        addUpdateBtn=findViewById(R.id.idBtnAddUpdate)
        nviewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        val noteType=intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle=intent.getStringExtra("noteTitle")
            val noteDesc=intent.getStringExtra("noteDescription")
            noteID=intent.getIntExtra("noteID",-1)
            addUpdateBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdt.setText(noteDesc)
        }
        else{
            addUpdateBtn.setText("Save Note")
        }
        addUpdateBtn.setOnClickListener{
            val noteTitle=noteTitleEdt.text.toString()
            val noteDescription=noteDescriptionEdt.text.toString()
            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() || noteDescription.isNotEmpty()){
                    val sdf=SimpleDateFormat("dd MM,yyyy -HH:mm")
                    val curretDate:String=sdf.format(Date())
                    val updatedNote= Note(noteTitle,noteDescription,curretDate)
                    updatedNote.id=noteID
                    nviewModel.updateNote(updatedNote)
                    Toast.makeText(this,"Note Updated...",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                if(noteTitle.isNotEmpty() || noteDescription.isNotEmpty()){
                    val sdf=SimpleDateFormat("dd MM,yyyy /HH:mm")
                    val curretDate:String=sdf.format(Date())
                    nviewModel.insertNote(Note(noteTitle,noteDescription,curretDate))
                    Toast.makeText(this,"Note Added...",Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext,NotesActivity::class.java))
            this.finish()} }
}