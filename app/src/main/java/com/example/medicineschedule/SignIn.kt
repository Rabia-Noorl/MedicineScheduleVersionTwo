package com.example.medicineschedule

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text


class SignIn : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText : EditText? = null
    var forgetPasstextView : TextView? = null
    var signIpBtn : Button? = null
    var signUptextViewS: TextView? = null
    var mProgressBar: ProgressBar? = null

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //Views initilization
         emailEditText = findViewById<TextView>(R.id.emailET) as EditText
         passwordEditText = findViewById<TextView>(R.id.userNameET) as EditText
         forgetPasstextView = findViewById<TextView>(R.id.forgetpassword) as TextView
         signIpBtn = findViewById<Button>(R.id.btnsignIn) as Button
         signUptextViewS = findViewById<TextView>(R.id.txtviewSignup) as TextView
         mProgressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        var textViewSignUp=findViewById<TextView>(R.id.txtviewSignup)

        textViewSignUp.setOnClickListener {
            val Intent2 = Intent(this,SignUp::class.java)
            startActivity(Intent2)
            this.finish();
        }

        authStateListener = FirebaseAuth.AuthStateListener()
        {
              //  updateUI()
        }

        signIpBtn?.setOnClickListener{
            signInUser()
        }
    }

    private fun signInUser() {
        // Change || to |
        if( !validateEmail() || !validatePasword())
        {
            return
        }
        var email:String = emailEditText?.text.toString().trim()
        var password:String = passwordEditText?.text.toString().trim()
        showProgressBar()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
            this,
            OnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    updateUI()
                    hideProgressBar()
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()

                } else {
                    hideProgressBar()
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            })
    }

    public fun updateUI() {
        val user = mAuth.currentUser
        if (user == null){
            Toast.makeText(this@SignIn, "Not logged in.....", Toast.LENGTH_SHORT).show()
            return
        }else{
            val intent: Intent = Intent(this,HomeScreen::class.java)
            startActivity(intent)
            finish()
            var userEmail:String = user.email.toString()
            Toast.makeText(this@SignIn, "User is logged in by $userEmail ", Toast.LENGTH_SHORT).show()
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