package com.example.medicineschedule

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_adapter_.*
import kotlinx.android.synthetic.main.activity_home_screen.*


class HomeScreen : AppCompatActivity() {

    private var searchBtn: Button? = null
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    private lateinit var navigationView:NavigationView
    private lateinit var navController:NavController
    private lateinit var bottomNavigationView:BottomNavigationView
    var textEmail:TextView?=null
    var textUserName:TextView?=null
    val toolbar:Toolbar?=null
    private  var currentUser: FirebaseUser? =mAuth.currentUser
    private lateinit var googleSignInClient: GoogleSignInClient
    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


//        searcBtn = findViewById<Button>(R.id.searcBtn) as Button

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);

        bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navController= Navigation.findNavController(this, R.id.frag_layout)
        drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView=findViewById<NavigationView>(R.id.navigation_view)
        textUserName=findViewById<TextView>(R.id.userNameTV)
        textEmail=findViewById<TextView>(R.id.userEmailTV)
        var googleSignInOptions= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
           "47830765431-h1prf0sul14dj5p1vdop0a6r315qt1kt.apps.googleusercontent.com")
            .requestEmail().build()
        googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions)

//requestIdToken(
//            getString(R.string.default_web_client_id)
//        )
//        searcBtn?.setOnClickListener {
//            val intent = Intent(this, DictionaryActivity::class.java)
//            startActivity(intent)
//        }
        setSupportActionBar(my_toolbar)
        actionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setupDrawerContent(navigationView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        updateNavheader()

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.help ->{


                }
                R.id.share ->{
                    val shareBody="Download Medica360 on Playstore: https://play.google.com&hl=en"
                    val shareSub="Medica 360"
                    val shareIntent=Intent(Intent.ACTION_SEND)
                    shareIntent.type="text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub)
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody)
                    startActivity(shareIntent)
                }
                R.id.rate ->{

                }
                R.id.signOut -> {
                    singOutUser()
                }
            }
            true
        }
    }

    private fun updateNavheader() {
        val navigationView:NavigationView=findViewById(R.id.navigation_view)
        val headerView: View =navigationView.getHeaderView(0)
        val navUsername:TextView=headerView.findViewById(R.id.userNameTV)
        val navUserEmail:TextView=headerView.findViewById(R.id.userEmailTV)
        val navUserphoto:ImageView=headerView.findViewById(R.id.profileimageView)
        navUserEmail.setText(currentUser?.email)
        navUsername.setText(currentUser?.displayName)
        Glide.with(this).load(currentUser?.photoUrl).into(navUserphoto)




    }

    private fun singOutUser() {
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            updateUI()
        }

    }

    private fun updateUI() {
        val firebaseUser=mAuth.currentUser
        if(firebaseUser==null)
        {
            //user not logged in
            startActivity(Intent(this, SignInWithGoogle::class.java))
            finish()
        }
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
                mAuth.removeAuthStateListener(authStateListener)
            }
        }
    }

}
