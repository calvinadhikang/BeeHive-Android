package com.example.beehive.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.beehive.R
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcelable
import android.util.Log
import android.widget.*
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.ListUserDRO
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.dao.AppDatabase
import com.example.beehive.dao.Beeworker
import com.example.beehive.dao.User
import com.example.beehive.data.Category
import com.example.beehive.data.ListCategoryDRO
import com.example.beehive.observerConnectivity.ConnectivityObserver
import com.example.beehive.utils.DialogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList


@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    lateinit var connectivityObserver: ConnectivityObserver //buat cek network aplikasi
    val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var db: AppDatabase
    var isLogin:Boolean = false
    var userLogin:UserData? = null

    var listCategory:List<Category> = listOf()
    var listBeeworker:List<UserData> = listOf()
    var rememberMeCheck:User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar!!.hide()

        db = AppDatabase.build(this)

        coroutine.launch {
            rememberMeCheck = db.userDAO.get()

            runOnUiThread{
                if(rememberMeCheck==null){
                    //tidak ada user yang lagi login
                    isLogin = false
                    fetchCategoryThenBeeworkerWithoutUserLogin()
                }else{
                    //ada user loged in
                    isLogin = true
                    reloadUser(rememberMeCheck!!.REMEMBER_TOKEN,true)
                }
            }
        }
    }

    fun reloadUser(REMEMBER_TOKEN:String, firstTime:Boolean = false){
        val client = ApiConfiguration.getApiService().getProfileNoAuth(remember_token =
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
                                isLogin = true
                                fetchCategoryThenBeeworker()
                            }else{
                                userLogin!!.NAMA = data.NAMA
                                userLogin!!.BALANCE = data.BALANCE!!
                                userLogin!!.PICTURE = data.PICTURE!!
                                userLogin!!.PICTURE = data.PICTURE!!
                                userLogin!!.BIO = data.BIO!!
                            }

                        }
                    }else{
                        Toast.makeText(this@SplashScreenActivity,
                            "Session ended, please login again", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    val statusCode:Int = response.code()

                    val dialogBinding = this@SplashScreenActivity.layoutInflater
                        .inflate(R.layout.dialog_layout,null)
                    val myDialog = Dialog(this@SplashScreenActivity)
                    myDialog.setContentView(dialogBinding)
                    myDialog.setCancelable(true)
                    myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    myDialog.show()
                    val txtHeader: TextView = dialogBinding.findViewById(R.id.txtHeaderModal)
                    txtHeader.text = "Session ended, please login again"

                    val btnOk: Button = dialogBinding.findViewById(R.id.btnOkModal)
                    btnOk.text = "Ok"
                    btnOk.setOnClickListener{
                        myDialog.dismiss()
                        isLogin = false
                        coroutine.launch {
                            db.userDAO.logout()
                            runOnUiThread{
                                fetchCategoryThenBeeworkerWithoutUserLogin()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserDRO>, t: Throwable) {
                Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
               DialogHelper.showModal(this@SplashScreenActivity,"Poor network connection, you will" +
                        " redirected to offline mode"){
                    fetchOffline(isLogin = true)
                }
            }

        })
    }

    fun fetchCategoryThenBeeworkerWithoutUserLogin(){
        val client = ApiConfiguration.getApiService().fetchCategory()
        client.enqueue(object: Callback<ListCategoryDRO> {
            override fun onResponse(call: Call<ListCategoryDRO>, response: retrofit2.Response<ListCategoryDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listCategory = responseBody.data as List<Category>
                        coroutine.launch {
                            db.categoryDAO.clear()
                            runOnUiThread {
                                for (i in 0 until listCategory.size){
                                    var cat:Category = listCategory[i]
                                    coroutine.launch {
                                        db.categoryDAO.insert(com.example.beehive.dao.Category(
                                            cat.ID_CATEGORY,cat.StingsRelatedCount,cat.NAMA_CATEGORY,
                                            cat.CREATED_AT,cat.DELETED_AT,cat.UPDATED_AT
                                        ))
                                        runOnUiThread {
                                            var z:Int = 0
                                        }
                                    }
                                }
                                fetchBeeworkerWithoutLogin()
                            }
                        }
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("ERROR FETCH CATEGORY", "Fail Access: $statusCode")
                    DialogHelper.showModal(this@SplashScreenActivity,"Poor network connection, you will" +
                            " redirected to offline mode"){
                        fetchOffline(isLogin = false)
                    }
                }
            }

            override fun onFailure(call: Call<ListCategoryDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
                DialogHelper.showModal(this@SplashScreenActivity,"Poor network connection, you will" +
                        " redirected to offline mode"){
                    fetchOffline(isLogin = false)
                }
            }

        })
    }
    fun fetchOffline(isLogin:Boolean){
        var tempList:List<com.example.beehive.dao.Category> = listOf()
        var tempListB:List<Beeworker> = listOf()
        coroutine.launch {
            tempList = db.categoryDAO.fetch()
            runOnUiThread {
                for(i in 0 until tempList.size){
                    var c = tempList[i]
                    listCategory+=Category(c.StingsRelatedCount,c.NAMA_CATEGORY,c.ID_CATEGORY,
                    c.CREATED_AT, listOf(),c.DELETED_AT,c.UPDATED_AT)
                }
                coroutine.launch {
                    tempListB = db.beeworkerDAO.fetch()
                    runOnUiThread {
                        for(i in 0 until tempListB.size){
                            var b = tempListB[i]
                            listBeeworker+=UserData(b.NAMA,b.TANGGAL_LAHIR,b.PICTURE,b.TANGGAL_LAHIR,
                            null,b.BIO,b.EMAIL,b.BALANCE,null,null,null,b.RATING,
                            null,b.TANGGAL_LAHIR,b.TANGGAL_LAHIR,b.REMEMBER_TOKEN,b.jumlahSting,b.jumlahOrderDone,
                            null)
                        }
                        if(listCategory.size<1 || listBeeworker.size<1){
                            DialogHelper.showModal(this@SplashScreenActivity,"Something went wrong, " +
                                    "please close this app and open it again ＞﹏＜"){
                                finish()
                            }
                        }else{
                            if(!isLogin){
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                                    intent.putExtra("is_login",false)
                                    intent.putExtra("is_online",false)
                                    intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                                    intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                                    startActivity(intent)
                                    finish()
                                }, 1000)
                            }else{
                                userLogin = UserData(rememberMeCheck!!.NAMA,
                                rememberMeCheck!!.CREATED_AT,rememberMeCheck!!.PICTURE,
                                rememberMeCheck!!.EMAIL_VERIFIED_AT,null,rememberMeCheck!!.BIO,
                                rememberMeCheck!!.EMAIL,rememberMeCheck!!.BALANCE,rememberMeCheck!!.SUBSCRIBED.toInt(),
                                rememberMeCheck!!.STATUS.toInt(),"",rememberMeCheck!!.RATING,
                                    null,rememberMeCheck!!.TANGGAL_LAHIR,
                                rememberMeCheck!!.UPDATED_AT,rememberMeCheck!!.REMEMBER_TOKEN,null,
                                    null,null)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    Log.i("LISTBEEWORKER",listBeeworker.toString())
                                    val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                                    intent.putExtra("is_login",true)
                                    intent.putExtra("is_online",false)
                                    intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                                    intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                                    intent.putExtra("user_login",userLogin)
                                    startActivity(intent)
                                    finish()
                                }, 1000)
                            }
                        }
                    }
                }
            }
        }
    }

    fun fetchBeeworkerWithoutLogin(){
        val client = ApiConfiguration.getApiService().fetchBeeworker()
        client.enqueue(object: Callback<ListUserDRO> {
            override fun onResponse(call: Call<ListUserDRO>, response: retrofit2.Response<ListUserDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listBeeworker = responseBody.data as List<UserData>
                        coroutine.launch {
                            db.beeworkerDAO.clear()
                            runOnUiThread {
                                for (i in 0 until listBeeworker.size){
                                    var bee:UserData = listBeeworker[i]
                                    coroutine.launch {
                                        db.beeworkerDAO.insert(Beeworker(bee.REMEMBER_TOKEN.toString(),
                                        bee.EMAIL.toString(),bee.NAMA.toString(),bee.PICTURE,bee.BIO,
                                        bee.BALANCE,bee.RATING,bee.TANGGAL_LAHIR,bee.jumlahSting,
                                        bee.jumlahOrderDone))
                                        runOnUiThread {
                                            var z:Int = 0
                                        }
                                    }
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                                    intent.putExtra("is_login",isLogin)
                                    intent.putExtra("is_online",true)
                                    intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                                    intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                                    startActivity(intent)
                                    finish()
                                }, 1000)
                            }
                        }
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("ERROR FETCH beeworker", "Fail Access: $statusCode")
                }
            }

            override fun onFailure(call: Call<ListUserDRO>, t: Throwable) {
                Log.e("ERROR FETCH beeworker", "onFailure: ${t.message}")
            }

        })
    }
    fun fetchCategoryThenBeeworker(){
        val client = ApiConfiguration.getApiService().fetchCategory()
        client.enqueue(object: Callback<ListCategoryDRO> {
            override fun onResponse(call: Call<ListCategoryDRO>, response: retrofit2.Response<ListCategoryDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listCategory = responseBody.data as List<Category>
                        coroutine.launch {
                            db.categoryDAO.clear()
                            runOnUiThread {
                                for (i in 0 until listCategory.size){
                                    var cat:Category = listCategory[i]
                                    coroutine.launch {
                                        db.categoryDAO.insert(com.example.beehive.dao.Category(
                                            cat.ID_CATEGORY,cat.StingsRelatedCount,cat.NAMA_CATEGORY,
                                            cat.CREATED_AT,cat.DELETED_AT,cat.UPDATED_AT
                                        ))
                                        runOnUiThread {
                                            var z:Int = 0
                                        }
                                    }
                                }
                                fetchBeeworker()
                            }
                        }

                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("ERROR FETCH CATEGORY", "Fail Access: $statusCode")
                }
            }

            override fun onFailure(call: Call<ListCategoryDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
            }

        })
    }
    fun fetchBeeworker(){
        val client = ApiConfiguration.getApiService().fetchBeeworker()
        client.enqueue(object: Callback<ListUserDRO> {
            override fun onResponse(call: Call<ListUserDRO>, response: retrofit2.Response<ListUserDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listBeeworker = responseBody.data as List<UserData>
                        coroutine.launch {
                            db.beeworkerDAO.clear()
                            runOnUiThread {
                                for (i in 0 until listBeeworker.size){
                                    var bee:UserData = listBeeworker[i]
                                    coroutine.launch {
                                        db.beeworkerDAO.insert(Beeworker(bee.REMEMBER_TOKEN.toString(),
                                            bee.EMAIL.toString(),bee.NAMA.toString(),bee.PICTURE,bee.BIO,
                                            bee.BALANCE,bee.RATING,bee.TANGGAL_LAHIR,bee.jumlahSting,
                                            bee.jumlahOrderDone))
                                        runOnUiThread {
                                            var z:Int = 0
                                        }
                                    }
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    Log.i("LISTBEEWORKER",listBeeworker.toString())
                                    val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                                    intent.putExtra("is_login",isLogin)
                                    intent.putExtra("is_online",true)
                                    intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                                    intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                                    intent.putExtra("user_login",userLogin)
                                    startActivity(intent)
                                    finish()
                                }, 1000)
                            }
                        }
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("ERROR FETCH beeworker", "Fail Access: $statusCode")
                }
            }

            override fun onFailure(call: Call<ListUserDRO>, t: Throwable) {
                Log.e("ERROR FETCH beeworker", "onFailure: ${t.message}")
            }

        })
    }
}