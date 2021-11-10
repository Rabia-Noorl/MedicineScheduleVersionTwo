package com.example.medicineschedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlin.math.log


open class SignIn : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    //constants
    private val RC_SIGN_IN=1001
    private val TAG="GOOGLE_SIGN_IN_TAG"


    var emailEditText: EditText? = null
    var passwordEditText : EditText? = null
    var forgetPasstextView : TextView? = null
    var signIpBtn : Button? = null
    var signUptextViewS: TextView? = null
    var mProgressBar: ProgressBar? = null
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var authStateListener: FirebaseAuth.AuthStateListener? = null
    var signInwithGoogle: Button?=null

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
        signInwithGoogle=findViewById(R.id.btngoogleSignIn)
        var textViewSignUp=findViewById<TextView>(R.id.txtviewSignup)
       //for google sign in
        //configure google sign in
        var googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)
        ).requestEmail().build()
//configure Google signin Client object
        googleSignInClient=GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
//        val account:GoogleSignInAccount
//        account= GoogleSignIn.getLastSignedInAccount(this)!!
        //sign in with google button
        btngoogleSignIn.setOnClickListener{
            Log.d(TAG,"onCreate: Begin google signIn")
            signInWithGoogle()
        }
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if(result!!.resultCode== Activity.RESULT_OK)
            {
                val intent=result.data
                Log.d(TAG,"OnActivityResult: Google SignIn Intent Result")
                val accountTask=GoogleSignIn.getSignedInAccountFromIntent(intent)
                handleGoogleSignIn(accountTask)
            }
        }
        textViewSignUp.setOnClickListener {
            val Intent2 = Intent(this, SignUp::class.java)
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
                if (task.isSuccessful) {
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
            val intent: Intent = Intent(this, HomeScreen::class.java)
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
    private fun signInWithGoogle(){
       val signInIntent= googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)

    }
    private fun updateUI(account: GoogleSignInAccount){
//        if(account!=null){
//            btnsignIn.visibility=View.GONE
//        }
//        else{
//            btnsignIn.visibility=View.VISIBLE
//        }
    }

@Override
//   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//    if(requestCode==RC_SIGN_IN)
//    {
//        Log.d(TAG,"OnActivityResult: Google SignIn Intent Result")
//        val accountTask=GoogleSignIn.getSignedInAccountFromIntent(data)
//        handleGoogleSignIn(accountTask)
//    } }

    private fun handleGoogleSignIn(accountTask: Task<GoogleSignInAccount>?) {
        try{
            val account= accountTask?.getResult(ApiException::class.java)
            firebaseAuthWithGoogleAccount(account)

        }
catch (e: ApiException){
    Toast.makeText(this,GoogleSignInStatusCodes.getStatusCodeString(e.statusCode),Toast.LENGTH_SHORT)
}}
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        Log.d(TAG,"firebaseAuthWithGoogleAccount:bgin firebaseauth with google account")
        val credential=GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener { authResult ->
            //log in success
            Log.d(TAG,"firebaseauthWithGoogleAccount: logged in")
            //get loged In user
            val firebaseUser=firebaseAuth.currentUser
            //get user info
            val uId=firebaseUser!!.uid
            val email=firebaseUser.email
            Log.d(TAG,"FirebaseAuthwithGoogleAcoount: uid:$uId")
            Log.d(TAG,"FirebaseAuthwithGoogleAcoount: email:$email")
            //check if user new or existing
            if(authResult.additionalUserInfo!!.isNewUser){
                Log.d(TAG,"FirebaseAuthwithGoogleAcoount: AccountCreated...\n$email")
                Toast.makeText(this,"Account Created....\n$email",Toast.LENGTH_SHORT).show() }
            else{
                //existing user logged in
                Log.d(TAG,"FirebaseAuthwithGoogleAcoount: Existing User...\n$email")
                Toast.makeText(this,"Logged In....\n$email",Toast.LENGTH_SHORT).show()
            }
            //start profile activity
            startActivity(Intent(this,HomeScreen::class.java))
            finish()

        }
            .addOnFailureListener{e->
                //login failed
                Log.d(TAG,"FirebaseAuthwithGoogleAcoount: Login Failed due to ${e.message}")
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show() }
    }
    private fun checkUser(){
        //check if user is logged or not
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            //user is already logged in
            startActivity(Intent(this,HomeScreen::class.java))
            finish()
        }

    }
}