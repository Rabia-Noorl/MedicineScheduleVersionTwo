package com.example.medicineschedule

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.medicineschedule.classes.MedicineSchedudle
import com.example.medicineschedule.databinding.ActivitySignInWithGoogleBinding
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in_with_google.*

class SignInWithGoogle :AppCompatActivity() {
    private lateinit var binding:ActivitySignInWithGoogleBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val TAG="GOOGLE_SIGN_IN_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInWithGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //configure google sign in
        var googleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)
        ).requestEmail().build()
        googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions)
        //init firebaseauth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        //google signin button click
     binding.googleSignInButton.setOnClickListener{
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
    }
    private fun signInWithGoogle(){
        val signInIntent= googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)

    }
    private fun handleGoogleSignIn(accountTask: Task<GoogleSignInAccount>?) {
        try{
            val account= accountTask?.getResult(ApiException::class.java)
            firebaseAuthWithGoogleAccount(account)

        }
        catch (e: ApiException){
            Toast.makeText(this, GoogleSignInStatusCodes.getStatusCodeString(e.statusCode), Toast.LENGTH_SHORT)
        }}
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        Log.d(TAG,"firebaseAuthWithGoogleAccount:bgin firebaseauth with google account")
        val credential= GoogleAuthProvider.getCredential(account!!.idToken,null)
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
                Toast.makeText(this,"Account Created....\n$email", Toast.LENGTH_SHORT).show() }
            else{
                //existing user logged in
                Log.d(TAG,"FirebaseAuthwithGoogleAcoount: Existing User...\n$email")
                Toast.makeText(this,"Logged In....\n$email", Toast.LENGTH_SHORT).show()
            }
            //start profile activity
            startActivity(Intent(this,HomeScreen::class.java))
            finish()

        }
            .addOnFailureListener{e->
                //login failed
                Log.d(TAG,"FirebaseAuthwithGoogleAcoount: Login Failed due to ${e.message}")
                Toast.makeText(this,"Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show() }
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
