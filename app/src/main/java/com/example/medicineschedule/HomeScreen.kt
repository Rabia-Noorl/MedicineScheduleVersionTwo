package com.example.medicineschedule

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeScreen : AppCompatActivity() {
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController= Navigation.findNavController(this,R.id.frag_layout)
        val drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        actionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        val navigationView=findViewById<NavigationView>(R.id.navigation_view)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {return true}
        return super.onOptionsItemSelected(item)
    }
}
