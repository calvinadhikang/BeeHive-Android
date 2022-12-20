package com.example.beehive

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.VolleyLog
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.lelang_sting.CreateLelangStingFragment
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.observerConnectivity.NetworkConnectivityObserver
import com.example.beehive.user_auth.UserBeforeLoginFragment
import com.example.beehive.user_profile.UserProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    lateinit var navbar: BottomNavigationView
    lateinit var frMain: FrameLayout
    lateinit var connectivityObserver: ConnectivityObserver //buat cek network aplikasi
    val coroutine = CoroutineScope(Dispatchers.IO)
    var isLogin:Boolean = false
     var userLogin:UserData? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         connectivityObserver = NetworkConnectivityObserver(applicationContext)

         frMain = findViewById(R.id.frMain)
         navbar = findViewById(R.id.navbarBeforeLogin)
//         VolleyLog.DEBUG = true;
         swapToFrag(LandingPageFragment(), Bundle())
         beforeLogin()
         navbar.setOnNavigationItemSelectedListener {
             return@setOnNavigationItemSelectedListener when(it.itemId){
                 R.id.menu_home->{
                     swapToFrag(LandingPageFragment(), Bundle())
                     true
                 }
                 R.id.menu_search->{
                     swapToFrag(SearchFragment(), Bundle())
                     true
                 }
                 R.id.menu_add->{
                     swapToFrag(CreateLelangStingFragment(),Bundle())
                     true
                 }
                 R.id.menu_notification->{
                     true
                 }
                 R.id.menu_profile->{
                     if(isLogin){
                         swapToFrag(UserProfileFragment(), Bundle())
                     }else {
                         swapToFrag(UserBeforeLoginFragment(), Bundle())
                     }
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
        isLogin = false
        val nav_Menu: Menu = navbar.menu
        nav_Menu.findItem(R.id.menu_add).isVisible = false
        nav_Menu.findItem(R.id.menu_notification).isVisible = false
        swapToFrag(UserBeforeLoginFragment(), Bundle())
    }
    fun afterLogin(){
        val nav_Menu: Menu = navbar.menu
        nav_Menu.findItem(R.id.menu_add).isVisible = true
        nav_Menu.findItem(R.id.menu_notification).isVisible = true
        isLogin = true
        swapToFrag(UserProfileFragment(), Bundle())
    }
    fun login(user:UserData){
        updateLogin(user)
        afterLogin()
    }
    fun logout(){
        userLogin = null
        beforeLogin()

    }
    fun updateLogin(user: UserData){
        userLogin = user
    }
    fun showModal(
        message:String,
        btnOkText:String = "OK",
        callbackFun:()->Unit
    ){

        val dialogBinding = this.layoutInflater
            .inflate(R.layout.dialog_layout,null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val txtHeader: TextView = dialogBinding.findViewById(R.id.txtHeaderModal)
        txtHeader.text = message

        val btnOk: Button = dialogBinding.findViewById(R.id.btnOkModal)
        btnOk.text = btnOkText
        btnOk.setOnClickListener{
            myDialog.dismiss()
            callbackFun()
        }
    }
    fun showConfirmation(
        message:String,
        btnSuccessText:String = "Yes",
        btnFailText:String = "No",
        callbackSuccess:()->Unit,
        callbackFail:()->Unit,){

        val dialogBinding = this.layoutInflater
            .inflate(R.layout.dialog_confirmation_layout,null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val txtHeader: TextView = dialogBinding.findViewById(R.id.txtHeaderModal)
        txtHeader.text = message

        val btnYes: Button = dialogBinding.findViewById(R.id.btnYesModal)
        btnYes.text = btnSuccessText
        btnYes.setOnClickListener{
            myDialog.dismiss()
            callbackSuccess()
        }
        val btnNo: Button = dialogBinding.findViewById(R.id.btnNoModal)
        btnNo.text = btnFailText
        btnNo.setOnClickListener{
            myDialog.dismiss()
            callbackFail()
        }
    }
    fun reloadUser(){
        val client = ApiConfiguration.getApiService().getProfile(remember_token = userLogin!!.REMEMBER_TOKEN!!)
        client.enqueue(object: Callback<UserDRO> {
            override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.data!=null){
                            var data:UserData = responseBody.data
                            userLogin!!.NAMA = data.NAMA
                            userLogin!!.BALANCE = data.BALANCE!!
                        }
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("GET_PROFILE_ERROR", "Fail Access: $statusCode")
                }
            }

            override fun onFailure(call: Call<UserDRO>, t: Throwable) {
                Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
            }

        })
    }
}
