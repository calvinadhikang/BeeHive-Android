package com.example.beehive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navbarBeforeLogin: BottomNavigationView
    lateinit var frMain: FrameLayout
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         frMain = findViewById(R.id.frMain)
         navbarBeforeLogin = findViewById(R.id.navbarBeforeLogin)
         swapToFrag(UserBeforeLoginFragment(), Bundle())
         navbarBeforeLogin.setOnNavigationItemSelectedListener {
             return@setOnNavigationItemSelectedListener when(it.itemId){
                 R.id.menu_home->{
                     swapToFrag(LandingPageFragment(), Bundle())
                     true
                 }
                 R.id.menu_search->{
//                     swapToFrag(RegisterFragment(),Bundle())
                     true
                 }
                 R.id.menu_profile->{
                     swapToFrag(UserBeforeLoginFragment(), Bundle())
                     true
                 }
                 else->false
             }
         }
    }
    private fun swapToFrag(fragment: Fragment, bundle:Bundle){
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frMain, fragment)
        fragmentManager.commit()
    }
}