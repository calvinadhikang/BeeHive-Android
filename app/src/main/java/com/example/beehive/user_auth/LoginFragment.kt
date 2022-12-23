package com.example.beehive.user_auth

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.data.UserLoginDTO
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.data.BasicDRO
import com.example.beehive.env
import com.example.beehive.observerConnectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class LoginFragment : Fragment() {
    var nameFrag:String = "LoginFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        val btnLogin: Button = view.findViewById(R.id.btnLogin)
        val txtLinkToRegister: TextView = view.findViewById(R.id.txtLinkToRegister)
        val txtPasswordLogin: EditText = view.findViewById(R.id.txtPasswordLogin)
        val txtEmailLogin: EditText = view.findViewById(R.id.txtEmailLogin)
        val btnBackLogin: ImageView = view.findViewById(R.id.btnBackLogin)
        txtLinkToRegister.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, RegisterFragment())
                .commit()
        }
        btnBackLogin.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserBeforeLoginFragment())
                .commit()
        }
        btnLogin.setOnClickListener {
            var emails: String = txtEmailLogin.text.toString()
            var pass: String = txtPasswordLogin.text.toString()
            if(emails==""||pass==""){
                acti.showModal("Semua input harus diisi"){}
                return@setOnClickListener
            }
            var loginTryUser: UserLoginDTO = UserLoginDTO(
                emails,pass
            )
            try {
                val client = ApiConfiguration.getApiService().login(userLoginData = loginTryUser)
                client.enqueue(object: Callback<UserDRO> {
                    override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
                        val responseBody = response.body()
                        if(response.isSuccessful){
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                    acti.login(responseBody.data)
                                    acti.showModal("Login Berhasil! Welcome ${responseBody.data.NAMA}"){}

                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                            if(statusCode==404){
                                acti.showModal("Email tidak terdaftar"){}
                            }
                            else if(statusCode==402){
                                acti.showModal("Password anda tidak sesuai"){}
                            }
                            else if(statusCode==405){

                                acti.showModal("Fitur untuk beeworker belum tersedia di mobile"){}
//
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserDRO>, t: Throwable) {
                        acti.showModal("Poor network connection detected"){}
                        Log.e("ErrorLogin", "onFailure: ${t.message}")
                    }
                })
            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                acti.showModal("Network Error!"){}
            }
        }
    }

}