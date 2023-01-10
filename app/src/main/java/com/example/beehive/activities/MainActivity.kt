package com.example.beehive.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beehive.*
import com.example.beehive.landing_page.LandingPageFragment
import com.example.beehive.NotificationFragment
import com.example.beehive.R
import com.example.beehive.SearchFragment
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.dao.AppDatabase
import com.example.beehive.dao.User
import com.example.beehive.data.Category
import com.example.beehive.data.ListCategoryDRO
import com.example.beehive.data.StingData
import com.example.beehive.landing_page.LandingPageAfterLoginFragment
import com.example.beehive.lelang_sting.CreateLelangStingFragment
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.observerConnectivity.NetworkConnectivityObserver
import com.example.beehive.stings.DetailCategoryFragment
import com.example.beehive.user_auth.UserBeforeLoginFragment
import com.example.beehive.user_profile.UserProfileFragment
import com.example.beehive.utils.DialogHelper
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
    var listBeeworker:List<UserData> = listOf()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         connectivityObserver = NetworkConnectivityObserver(applicationContext)

         frMain = findViewById(R.id.frMain)
         navbar = findViewById(R.id.navbarBeforeLogin)
         db = AppDatabase.build(this)
         try {
             isLogin = intent.getBooleanExtra("is_login",false)
             listCategory = intent.getParcelableArrayListExtra<Category>("list_category") as List<Category>
             listBeeworker = intent.getParcelableArrayListExtra<UserData>("list_beeworker") as List<UserData>
             if(!isLogin){
                 val nav_Menu: Menu = navbar.menu
                 nav_Menu.findItem(R.id.menu_add).isVisible = false
                 nav_Menu.findItem(R.id.menu_notification).isVisible = false
                 swapToFrag(LandingPageFragment(listCategory,listBeeworker), Bundle())
             }else{
                 userLogin = intent.getParcelableExtra("user_login")
                 afterFetchRememberMe()
             }
         }catch (e:Error){
             Log.e("Failed_intent",e.message.toString())
         }

         navbar.setOnNavigationItemSelectedListener {
             return@setOnNavigationItemSelectedListener when(it.itemId){
                 R.id.menu_home ->{
                     if(isLogin){
                         swapToFrag(LandingPageAfterLoginFragment(listCategory,listBeeworker), Bundle())
                     }else{
                         swapToFrag(LandingPageFragment(listCategory,listBeeworker), Bundle())
                     }
                     true
                 }
                 R.id.menu_search ->{
                     swapToFrag(SearchFragment(listCategory), Bundle())
                     true
                 }
                 R.id.menu_add ->{
                     swapToFrag(CreateLelangStingFragment(),Bundle())
                     true
                 }
                 R.id.menu_notification ->{
                     swapToFrag(NotificationFragment(),Bundle())
                     true
                 }
                 R.id.menu_profile ->{
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

    public fun search(key: String = ""){
        swapToFrag(SearchFragment(listCategory, key), Bundle())
    }

    public fun detailCategory(key: String, namaCategory:String){
        swapToFrag(DetailCategoryFragment(key,namaCategory), Bundle())
    }

    public fun detailSting(key: String, dataSting : StingData, fragmentBefore:Fragment){
        swapToFrag(DetailStingFragment(key,dataSting,fragmentBefore), Bundle())
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
        swapToFrag(LandingPageAfterLoginFragment(listCategory,listBeeworker), Bundle())
    }
    fun login(user:UserData){
        updateLogin(user)
        var rememberme: User = User(user.REMEMBER_TOKEN!!,user.EMAIL!!,user.NAMA!!)
        coroutine.launch {
            db.userDAO.insert(rememberme)  //ini untuk save isi user
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
         DialogHelper.showModal(this,message,btnOkText,callbackFun)
    }
    fun showConfirmation(
        message:String,
        btnSuccessText:String = "Yes",
        btnFailText:String = "No",
        callbackSuccess:()->Unit,
        callbackFail:()->Unit,)
    {
        DialogHelper.showConfirmation(this,message, btnSuccessText, btnFailText, callbackSuccess, callbackFail)
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
                            var data:UserData = responseBody.data!!
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

    public fun test(){
        val bottomsheet = BottomSheetFragment()
        bottomsheet.show(supportFragmentManager, "BottomSheetDialog")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
        }
        Toast.makeText(this,
            "bruh", Toast.LENGTH_SHORT).show()
        return true
//        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showConfirmation("Apakah anda yakin untuk keluar?","Yes","No",
            {
                finish()
            },{})
    }
}
