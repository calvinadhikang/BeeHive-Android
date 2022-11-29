package com.example.beehive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.android.volley.VolleyLog
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.observerConnectivity.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var navbarBeforeLogin: BottomNavigationView
    lateinit var frMain: FrameLayout
    val WS_HOST = "https://mhs.sib.stts.edu/k3behive/api"
    lateinit var connectivityObserver: ConnectivityObserver //buat cek network aplikasi
    val coroutine = CoroutineScope(Dispatchers.IO)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         connectivityObserver = NetworkConnectivityObserver(applicationContext)

         frMain = findViewById(R.id.frMain)
         navbarBeforeLogin = findViewById(R.id.navbarBeforeLogin)
         VolleyLog.DEBUG = true;
         swapToFrag(UserBeforeLoginFragment(), Bundle())
         navbarBeforeLogin.setOnNavigationItemSelectedListener {
             return@setOnNavigationItemSelectedListener when(it.itemId){
                 R.id.menu_home->{
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