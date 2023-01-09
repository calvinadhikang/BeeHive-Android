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
import com.example.beehive.dao.User
import com.example.beehive.data.Category
import com.example.beehive.data.ListCategoryDRO
import com.example.beehive.observerConnectivity.ConnectivityObserver
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar!!.hide()

        db = AppDatabase.build(this)
        var rememberMeCheck:User? = null

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
                        fetchBeeworkerWithoutLogin()
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
    fun fetchBeeworkerWithoutLogin(){
        val client = ApiConfiguration.getApiService().fetchBeeworker()
        client.enqueue(object: Callback<ListUserDRO> {
            override fun onResponse(call: Call<ListUserDRO>, response: retrofit2.Response<ListUserDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listBeeworker = responseBody.data as List<UserData>

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                            intent.putExtra("is_login",isLogin)
                            intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                            intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                            startActivity(intent)
                            finish()
                        }, 1000)
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
                        fetchBeeworker()
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
                        Handler(Looper.getMainLooper()).postDelayed({
                            Log.i("LISTBEEWORKER",listBeeworker.toString())
                            val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                            intent.putExtra("is_login",isLogin)
                            intent.putParcelableArrayListExtra("list_category",listCategory as ArrayList<out Parcelable>)
                            intent.putParcelableArrayListExtra("list_beeworker",listBeeworker as ArrayList<out Parcelable>)
                            intent.putExtra("user_login",userLogin)
                            startActivity(intent)
                            finish()
                        }, 1000)
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