package com.example.medicineschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DictionaryActivity : AppCompatActivity() {

    companion object{
        var drugName:String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
    }
}