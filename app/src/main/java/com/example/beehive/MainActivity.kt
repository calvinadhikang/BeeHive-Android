package com.example.beehive

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.dao.AppDatabase
import com.example.beehive.dao.User
import com.example.beehive.data.Category
import com.example.beehive.data.ListCategoryDRO
import com.example.beehive.data.ListStingDRO
import com.example.beehive.data.ListTransactionStingDRO
import com.example.beehive.lelang_sting.CreateLelangStingFragment
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.observerConnectivity.NetworkConnectivityObserver
import com.example.beehive.user_auth.UserBeforeLoginFragment
import com.example.beehive.user_profile.UserProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    lateinit var navbar: BottomNavigationView
    lateinit var frMain: FrameLayout
    lateinit var connectivityObserver: ConnectivityObserver //buat cek network aplikasi
    val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var db: AppDatabase
    var isLogin:Boolean = false
    var userLogin:UserData? = null

    var listCategory:List<Category> = listOf()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         connectivityObserver = NetworkConnectivityObserver(applicationContext)

         frMain = findViewById(R.id.frMain)
         navbar = findViewById(R.id.navbarBeforeLogin)
         db = AppDatabase.build(this)

         var rememberMeCheck:User? = null
         coroutine.launch {
             rememberMeCheck = db.userDAO.get()

             runOnUiThread{
                 if(rememberMeCheck==null){
                     //tidak ada user yang lagi login
                     isLogin = false
                     val nav_Menu: Menu = navbar.menu
                     nav_Menu.findItem(R.id.menu_add).isVisible = false
                     nav_Menu.findItem(R.id.menu_notification).isVisible = false
                     swapToFrag(LandingPageFragment(), Bundle())
                 }else{
                     //ada user loged in
                     reloadUser(rememberMeCheck!!.REMEMBER_TOKEN,true)
                     swapToFrag(LandingPageFragment(), Bundle())
                 }
             }
         }

//         val client = ApiConfiguration.getApiService().fetchTransactionStingByCategory(category = 5)
//         client.enqueue(object: Callback<ListTransactionStingDRO> {
//             override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
//                 if(response.isSuccessful){
//                     val responseBody = response.body()
//                     if(responseBody!=null){
//                         Log.i("ENRICOASD",responseBody.toString())
//                         showModal("FETCH DONE"){}
//
//                     }
//                 }
//                 else{
//                     val statusCode:Int = response.code()
//                     val message:String = response.body()!!.message!!
//                     Log.e("ERROR FETCH STING", "Fail Access: $statusCode")
//                     Toast.makeText(this@MainActivity,
//                         message.toString(), Toast.LENGTH_SHORT).show()
//                 }
//             }
//
//             override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
//                 Log.e("ERROR FETCH", "onFailure: ${t.message}")
//             }
//
//         })
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
                         try {
                            swapToFrag(UserProfileFragment(), Bundle())
                         }catch (e:Error){
                             Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
                         }
                     }else {
                         swapToFrag(UserBeforeLoginFragment(), Bundle())
                     }
                     true
                 }
                 else->false
             }
         }
    }
    fun fetchCategory(){
        val client = ApiConfiguration.getApiService().fetchCategory()
        client.enqueue(object: Callback<ListCategoryDRO> {
            override fun onResponse(call: Call<ListCategoryDRO>, response: retrofit2.Response<ListCategoryDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listCategory = responseBody.data as List<Category>

                    }
                }
                else{
                    val statusCode:Int = response.code()
                    val message:String = response.body()!!.message!!
                    Log.e("ERROR FETCH CATEGORY", "Fail Access: $statusCode")
                    Toast.makeText(this@MainActivity,
                        message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListCategoryDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
            }

        })
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
        fetchCategory()
        swapToFrag(UserProfileFragment(), Bundle())
    }
    fun afterFetchRememberMe(){
        val nav_Menu: Menu = navbar.menu
        nav_Menu.findItem(R.id.menu_add).isVisible = true
        nav_Menu.findItem(R.id.menu_notification).isVisible = true
        isLogin = true
        fetchCategory()
        swapToFrag(LandingPageFragment(), Bundle())
    }
    fun login(user:UserData){
        updateLogin(user)
        var rememberme: User = User(user.REMEMBER_TOKEN!!,user.EMAIL!!,user.NAMA!!)
        coroutine.launch {
            db.userDAO.insert(rememberme)  //ini ngesave isi user
        }
        afterLogin()
    }
    fun logout(){
        userLogin = null
        coroutine.launch {
            db.userDAO.logout()
        }
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
    fun reloadUser(REMEMBER_TOKEN:String, firstTime:Boolean = false){
        val client = ApiConfiguration.getApiService().getProfile(remember_token =
        REMEMBER_TOKEN)
        client.enqueue(object: Callback<UserDRO> {
            override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.data!=null){
                            var data:UserData = responseBody.data
                            if(firstTime){
                                userLogin = data
                                afterFetchRememberMe()
                            }else{
                                userLogin!!.NAMA = data.NAMA
                                userLogin!!.BALANCE = data.BALANCE!!
                                userLogin!!.PICTURE = data.PICTURE!!
                                userLogin!!.PICTURE = data.PICTURE!!
                                userLogin!!.BIO = data.BIO!!
                            }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
        }
        Toast.makeText(this,
            "bruh", Toast.LENGTH_SHORT).show()
        return true
//        return super.onOptionsItemSelected(item)
    }

//    <ImageView
//    android:id="@+id/imageView6"
//    android:layout_width="50dp"
//    android:layout_height="50dp"
//    android:layout_marginBottom="15dp"
//    android:background="@drawable/button_nav_add"
//    android:padding="6dp"
//    app:layout_constraintBottom_toBottomOf="parent"
//    app:layout_constraintEnd_toEndOf="@+id/navbarBeforeLogin"
//    app:layout_constraintStart_toStartOf="parent"
//    app:srcCompat="@drawable/add_white" />
}
