package com.example.beehive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
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
import org.json.JSONObject
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    lateinit var txtEmailLogin:EditText
    lateinit var txtPasswordLogin:EditText
    lateinit var txtLinkToRegister:TextView
    lateinit var btnLogin:Button

    val WS_HOST = "https://mhs.sib.stts.edu/k3behive/api"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmailLogin = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin)
        txtLinkToRegister = findViewById(R.id.txtLinkToRegister)
        btnLogin = findViewById(R.id.btnLogin)

        txtLinkToRegister.setOnClickListener {
            //to register
        }
        btnLogin.setOnClickListener {
            var emails: String = txtEmailLogin.text.toString()
            var pass: String = txtPasswordLogin.text.toString()
            if(emails==""||pass==""){
                Toast.makeText(this@LoginActivity,
                    "Semua input harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val requestBody = "email=${emails}" + "&password=${pass}"

            val strReq = object: StringRequest(
                Method.POST,
                "$WS_HOST/login",
                Response.Listener {
                    val obj: JSONObject = JSONObject(it)
                    val objData:JSONObject = obj.getJSONObject("data")
                    Log.i("LOGINRESULT",obj.getString("message"))
//                    Log.i("USERINFO",objData.getString("EMAIL","").toString())
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Fetch Error!", Toast.LENGTH_SHORT).show()
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
                    Log.d("errormessage",message)
                    Looper.prepare()
                    Toast.makeText(this@LoginActivity,
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
                        var datax = data.getJSONObject("data")
                        var exceptionEmail:String? = datax.getString("email")
                        if(exceptionEmail!=null){
                            Log.d("DATA2",exceptionEmail)
                        }
                        if(response.statusCode==200){
                            Looper.prepare()
                            Toast.makeText(this@LoginActivity,
                                message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    return super.parseNetworkResponse(response)
                }
            }
            val queue : RequestQueue = Volley.newRequestQueue(this)
            queue.add(strReq)

        }
    }
}