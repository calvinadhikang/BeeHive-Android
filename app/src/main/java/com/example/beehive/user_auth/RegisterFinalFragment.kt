package com.example.beehive.user_auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.data.UserRegisterDTO
import com.example.beehive.env
import retrofit2.Call
import retrofit2.Callback
import java.util.*


class RegisterFinalFragment(
    var email:String,
    var role:Int //1 farmer 2 beeworker
) : Fragment() {
    var nameFrag:String = "RegisterFinalFragment"
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
        return inflater.inflate(R.layout.fragment_register_final, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var txtLinkToLogin2:TextView = view.findViewById(R.id.txtLinkToLogin2)
        var btnBackRegisterFinal:ImageButton = view.findViewById(R.id.btnBackRegisterFinal)
        var btnRegister:Button = view.findViewById(R.id.btnRegister)
        var txtPasswordRegister:EditText = view.findViewById(R.id.txtPasswordRegister)
        var txtConfirmPasswordRegister:EditText = view.findViewById(R.id.txtConfirmPasswordRegister)
        var txtNameRegister:EditText = view.findViewById(R.id.txtNameRegister)
        var txtDateRegister:DatePicker = view.findViewById(R.id.txtDateRegister)

        txtLinkToLogin2.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, LoginFragment())
                .commit()
        }
        btnBackRegisterFinal.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, RegisterFragment())
                .commit()
        }
        btnRegister.setOnClickListener{
            var password:String = txtPasswordRegister.text.toString()
            var confirm:String = txtConfirmPasswordRegister.text.toString()
            var name:String = txtNameRegister.text.toString()
            val acti = activity as MainActivity
            val WS_HOST:String = env.URL
            val day: Int = txtDateRegister.dayOfMonth
            val month: Int = txtDateRegister.month
            val year: Int = txtDateRegister.year
            //2001-02-26 13:43:43
            val dateFormat = "${year}-${month}-${day} 10:10:10"
            var calendar:Calendar = Calendar.getInstance();
            var yearNow:Int = calendar.get(Calendar.YEAR);
            if(password==""||confirm==""||name==""){
                acti.showModal("Ada field kosong!"){}
                return@setOnClickListener
            }
            if(password!=confirm){
                acti.showModal("Password dan konfirmasi harus sama!"){}
                return@setOnClickListener
            }
            if(password.length<8){
                acti.showModal("Panjang password minimal 8 karakter!"){}
                return@setOnClickListener
            }
            if(yearNow-18 < year){
                acti.showModal("Anda harus berusia minimal 18 tahun!"){}
                return@setOnClickListener
            }
            var registerTryUser:UserRegisterDTO = UserRegisterDTO(
                email,password,name,role,dateFormat
            )
            try{
                val client = ApiConfiguration.getApiService().register(userRegisterData = registerTryUser)
                client.enqueue(object: Callback<UserDRO> {
                    override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                    acti.showModal("Register Berhasil!"){}
                                    txtNameRegister.setText("")
                                    txtPasswordRegister.setText("")
                                    txtConfirmPasswordRegister.setText("")

                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, LoginFragment())
                                        .commit()
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e(nameFrag, "Fail Access: $statusCode")
                            if(statusCode==404){
                                acti.showModal("Email Sudah Digunakan"){}
                            }
                            else if(statusCode==403){
                                acti.showModal("Panjang password minimal 8 karakter"){}
                            }
                            else if(statusCode==500){
                                acti.showModal("Register failed"){}
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserDRO>, t: Throwable) {
                        Log.e(nameFrag, "onFailure: ${t.message}")
                        acti.showModal("Poor network connection detected"){}
                    }
                })
            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                acti.showModal("Network Error!"){}
            }
        }
    }
}