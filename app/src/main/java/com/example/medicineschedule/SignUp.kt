package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val textViewSignIn = findViewById<TextView>(R.id.txtalreadyAccount) as TextView
   textViewSignIn.setOnClickListener(fun(it: View) {
       val Intent1 = Intent(this, SignIn::class.java)
       startActivity(Intent1)
   })
   }

}