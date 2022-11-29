package com.example.beehive

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.beehive.observerConnectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.Charset

class LoginFragment : Fragment() {

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
        val WS_HOST:String = acti.WS_HOST
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
            asynch()
            try{
                var emails: String = txtEmailLogin.text.toString()
                var pass: String = txtPasswordLogin.text.toString()
                if(emails==""||pass==""){
                    Toast.makeText(requireContext(),
                        "Semua input harus diisi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val requestBody = "email=${emails}&password=${pass}"

                val strReq = object: StringRequest(
                    Method.POST,
                    "$WS_HOST/login",
                    Response.Listener {
                        val obj: JSONObject = JSONObject(it)
                        val objData: JSONObject = obj.getJSONObject("data")
                        Log.i("LOGINRESULT",obj.getString("message"))
                        Toast.makeText(requireContext(),
                            obj.getString("message"), Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener {
                        Log.e("RESPONSEERROR",it.message.toString())
//                        Toast.makeText(requireContext(), "Fetch Error for ${emails}", Toast.LENGTH_SHORT).show()
                    }
                ){
                    override fun getBody(): ByteArray {
                        return requestBody.toByteArray(Charset.defaultCharset())
                    }

                    override fun parseNetworkError(volleyError: VolleyError?): VolleyError {

                        val responseBody = String(volleyError!!.networkResponse.data,
                            charset("utf-8"))
                        val data = JSONObject(responseBody)

                        val errorCode:Int = volleyError!!.networkResponse.statusCode
                        var message = data.getString("message")
                        Log.e("errormessage",message)
                        Looper.prepare()
                        Toast.makeText(requireContext(),
                            message, Toast.LENGTH_SHORT).show()
                        return super.parseNetworkError(volleyError)
                    }
                    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                        if(response!=null) {
                            Log.d("STTSCODE", response.statusCode.toString())
                            val responseBody = String(response.data,
                                charset("utf-8"))
                            val data = JSONObject(responseBody)
                            var message = data.getString("message")
                            var isSuccess = data.getBoolean("success")
                            if(isSuccess){
                                var datax = data.getJSONObject("data")
                                var exceptionEmail:String? = datax.getString("EMAIL")
                                var exceptionNama:String? = datax.getString("NAMA")
                                var exceptionToken:String? = datax.getString("REMEMBER_TOKEN")
                                var exceptionStatus:String? = datax.getString("STATUS")
                                var exceptionSubscribe:String? = datax.getString("SUBSCRIBED")
                                if(exceptionEmail!=null){
                                    Log.d("EMAILMASUK",exceptionEmail)
                                }
                                if(response.statusCode==200){
                                    Looper.prepare()
                                    Toast.makeText(requireContext(),
                                        message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        return super.parseNetworkResponse(response)
                    }
                }
                val queue : RequestQueue = Volley.newRequestQueue(requireContext())
                queue.add(strReq)

            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                Toast.makeText(requireContext(),
                    "Network Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}