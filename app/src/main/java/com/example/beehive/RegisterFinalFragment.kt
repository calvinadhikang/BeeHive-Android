package com.example.beehive

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.data.UserRegisterDTO
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.nio.charset.Charset
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
                .replace(R.id.frMain,LoginFragment())
                .commit()
        }
        btnBackRegisterFinal.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,RegisterFragment())
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
                Toast.makeText(requireContext(),
                    "Ada field kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password!=confirm){
                Toast.makeText(requireContext(),
                    "Password dan konfirmasi harus sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length<8){
                Toast.makeText(requireContext(),
                        "Panjang password minimal 8 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(yearNow-18 < year){
                Toast.makeText(requireContext(),
                    "Anda harus berusia minimal 18 tahun!", Toast.LENGTH_SHORT).show()
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
                                    Toast.makeText(requireContext(),
                                        "Register Berhasil! Hello ${responseBody.data.NAMA}", Toast.LENGTH_SHORT).show()
                                    txtNameRegister.setText("")
                                    txtPasswordRegister.setText("")
                                    txtConfirmPasswordRegister.setText("")

                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain,LoginFragment())
                                        .commit()
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e(nameFrag, "Fail Access: $statusCode")
                            if(statusCode==404){
                                Toast.makeText(requireContext(),
                                    "Email Sudah Digunakan", Toast.LENGTH_SHORT).show()
                            }
                            else if(statusCode==403){
                                Toast.makeText(requireContext(),
                                    "Panjang password minimal 8 karakter", Toast.LENGTH_SHORT).show()
                            }
                            else if(statusCode==403){
                                Toast.makeText(requireContext(),
                                    "Panjang password minimal 8 karakter", Toast.LENGTH_SHORT).show()
                            }
                            else if(statusCode==500){
                                Toast.makeText(requireContext(),
                                    "Register failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserDRO>, t: Throwable) {
                        Log.e(nameFrag, "onFailure: ${t.message}")
                    }
                })
            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                Toast.makeText(requireContext(),
                    "Network Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}