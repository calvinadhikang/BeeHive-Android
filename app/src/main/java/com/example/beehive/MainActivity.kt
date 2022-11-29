package com.example.beehive

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.VolleyLog
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.observerConnectivity.NetworkConnectivityObserver
import com.example.beehive.user_auth.UserBeforeLoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class MainActivity : AppCompatActivity() {
    lateinit var navbar: BottomNavigationView
    lateinit var frMain: FrameLayout
    lateinit var connectivityObserver: ConnectivityObserver //buat cek network aplikasi
    val coroutine = CoroutineScope(Dispatchers.IO)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         connectivityObserver = NetworkConnectivityObserver(applicationContext)

         frMain = findViewById(R.id.frMain)
         navbar = findViewById(R.id.navbarBeforeLogin)
         VolleyLog.DEBUG = true;
         swapToFrag(LandingPageFragment(), Bundle())

            beforeLogin()
         navbar.setOnNavigationItemSelectedListener {
             return@setOnNavigationItemSelectedListener when(it.itemId){
                 R.id.menu_home->{
                     swapToFrag(LandingPageFragment(), Bundle())
                     true
                 }
                 R.id.menu_search->{
                     true
                 }
                 R.id.menu_add->{
                     true
                 }
                 R.id.menu_notification->{
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
    fun beforeLogin(){
        val nav_Menu: Menu = navbar.menu
        nav_Menu.findItem(R.id.menu_add).isVisible = false
        nav_Menu.findItem(R.id.menu_notification).isVisible = false
    }
    fun afterLogin(){
        val nav_Menu: Menu = navbar.menu
        nav_Menu.findItem(R.id.menu_add).isVisible = true
        nav_Menu.findItem(R.id.menu_notification).isVisible = true
    }
}
