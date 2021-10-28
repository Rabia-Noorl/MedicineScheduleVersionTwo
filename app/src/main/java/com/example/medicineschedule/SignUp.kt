package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private var userNameEditText: EditText? = null
    private var emailEditText : EditText? = null
    private var passwordEditText : EditText? = null
    private var confirmPasswrdEditText : EditText? = null
    private var signIUpBtn : Button? = null
    private var alreadyHaveAcc: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        userNameEditText = findViewById<EditText>(R.id.userNameET) as EditText
        emailEditText  = findViewById<EditText>(R.id.emailET) as EditText
        passwordEditText  = findViewById<EditText>(R.id.passwordET) as EditText
        confirmPasswrdEditText  = findViewById<EditText>(R.id.confirmPasswrdET) as EditText
        signIUpBtn  = findViewById<Button>(R.id.signUpBtn) as Button
        alreadyHaveAcc =findViewById<TextView>(R.id.txtalreadyAccount) as TextView

        authStateListener = FirebaseAuth.AuthStateListener()
        {
          //  updateUI()
        }

        signIUpBtn!!.setOnClickListener{
            creatUser()
        }

        alreadyHaveAcc?.setOnClickListener(fun(it: View) {
         val Intent1 = Intent(this, SignIn::class.java)
         startActivity(Intent1)
            this.finish();
        })
   }

    private fun creatUser() {
        if( !validateEmail() || !validatePasword())
        {
            return
        }
        var email:String = emailEditText?.text.toString().trim()
        var password:String = passwordEditText?.text.toString().trim()
        var userNaame:String = passwordEditText?.text.toString().trim()

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
            if(task.isSuccessful){
                updateUI()
                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun updateUI() {
        val user = mAuth.currentUser
        if (user == null){
            Toast.makeText(this@SignUp, "Not logged in.....", Toast.LENGTH_SHORT).show()
            return
        }else{
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
            var userEmail:String = user.email.toString()
            Toast.makeText(this@SignUp, "User is logged in by $userEmail ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePasword(): Boolean {
        val password: String = passwordEditText?.getText().toString().trim()

        return if (password.isEmpty()) {
            passwordEditText?.setError("Password is required. Can't be empty.")
            false
        } else if (password.length < 6) {
            passwordEditText?.setError("Password length short. Minimum 6 characters required.")
            true
        } else {
            passwordEditText?.setError(null)
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email: String = emailEditText?.getText().toString().trim()

        return if (email.isEmpty()) {
            emailEditText?.setError("Email is required. Can't be empty.")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText?.setError("Invalid Email. Enter valid email address.")
            false
        } else {
            emailEditText?.setError(null)
            true
        }
    }

    private fun showProgressBar() {
        mProgressBar?.setIndeterminate(true)
        mProgressBar?.setVisibility(View.VISIBLE)
    }

    private fun hideProgressBar() {
        mProgressBar?.setVisibility(View.GONE)
    }
    override fun onResume() {
        super.onResume()
        mAuth!!.addAuthStateListener(this.authStateListener!!)
    }

    override fun onPause() {
        super.onPause()
        if (authStateListener != null)
        {
            if (mAuth == null)
            {
                mAuth.removeAuthStateListener(authStateListener!!)
            }
        }
    }
}
