package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val textViewSignUp = findViewById<TextView>(R.id.txtviewSignup) as TextView
        textViewSignUp.setOnClickListener {
            val Intent2 = Intent(this,HomeScreen::class.java)
            startActivity(Intent2)
        }
    }
}