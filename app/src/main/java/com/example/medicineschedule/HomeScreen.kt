package com.example.medicineschedule

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class HomeScreen : AppCompatActivity() {


    private var searcBtn: Button? = null
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
       // updateUI()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

//        searcBtn = findViewById<Button>(R.id.searcBtn) as Button

        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController= Navigation.findNavController(this, R.id.frag_layout)
        val drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView=findViewById<NavigationView>(R.id.navigation_view)

//        searcBtn?.setOnClickListener {
//            val intent = Intent(this, DictionaryActivity::class.java)
//            startActivity(intent)
//        }
        actionBarDrawerToggle=ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open,
            R.string.close
        )

        setupDrawerContent(navigationView)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
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
                R.id.signOut -> {
                    singOutUser()
                }
            }
            true
        }
    }

    private fun singOutUser() {
        FirebaseAuth.getInstance().signOut()
        updateUI()
    }

    private fun updateUI() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
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
