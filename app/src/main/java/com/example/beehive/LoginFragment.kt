package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.beehive.data.UserLoginDTO
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
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
     fun asynch(){
        val acti = activity as MainActivity
        val statusNetwork: Flow<ConnectivityObserver.Status> = acti.connectivityObserver.observe()
         acti.coroutine.launch {
             var g = statusNetwork.collect()
             acti.runOnUiThread{
                 Toast.makeText(requireContext(),
                     "${g}", Toast.LENGTH_SHORT).show()
             }
         }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        val WS_HOST:String = env.URL
        val btnLogin: Button = view.findViewById(R.id.btnLogin)
        val txtLinkToRegister: TextView = view.findViewById(R.id.txtLinkToRegister)
        val txtPasswordLogin: EditText = view.findViewById(R.id.txtPasswordLogin)
        val txtEmailLogin: EditText = view.findViewById(R.id.txtEmailLogin)
        txtLinkToRegister.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,RegisterFragment())
                .commit()
        }
        btnLogin.setOnClickListener {
            var emails: String = txtEmailLogin.text.toString()
            var pass: String = txtPasswordLogin.text.toString()
            if(emails==""||pass==""){
                Toast.makeText(requireContext(),
                    "Semua input harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var loginTryUser: UserLoginDTO = UserLoginDTO(
                emails,pass
            )
//            https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//            asynch()
            try {

                val client = ApiConfiguration.getApiService().login(userLoginData = loginTryUser)
                client.enqueue(object: Callback<UserDRO> {
                    override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
//                            tvInfo.text = responseBody.weather[0].description
                                if(responseBody.data!=null){
                                    Toast.makeText(requireContext(),
                                        "Login Berhasil! Welcome ${responseBody.data.NAMA}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e(nameFrag, "Fail Access: $statusCode")
                            if(statusCode==404){
                                Toast.makeText(requireContext(),
                                    "Email tidak terdaftar", Toast.LENGTH_SHORT).show()
                            }
                            else if(statusCode==402){
                                Toast.makeText(requireContext(),
                                    "Password anda tidak sesuai", Toast.LENGTH_SHORT).show()
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