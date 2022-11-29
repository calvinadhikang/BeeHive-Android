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
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.nio.charset.Charset


class RegisterFinalFragment(
    var email:String,
    var role:Int //1 farmer 2 beeworker
) : Fragment() {
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
        var btnBackRegisterFinal:FloatingActionButton = view.findViewById(R.id.btnBackRegisterFinal)
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
            val WS_HOST:String = acti.WS_HOST
            val day: Int = txtDateRegister.dayOfMonth
            val month: Int = txtDateRegister.month
            val year: Int = txtDateRegister.year
            //2001-02-26 13:43:43
            val dateFormat = "${year}-${month}-${day} 10:10:10"
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

            try{
                val requestBody = "email=${email}&password=${password}&name=${name}" +
                        "&role=${role}&birthday=${dateFormat}"

                val strReq = object: StringRequest(
                    Method.POST,
                    "$WS_HOST/register",
                    Response.Listener {
                        val obj: JSONObject = JSONObject(it)
                        val objData: JSONObject = obj.getJSONObject("data")
                        Log.i("REGISTERRESULT",obj.getString("message"))
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
//                                var exceptionEmail:String? = datax.getString("EMAIL")
//                                var exceptionNama:String? = datax.getString("NAMA")
                                if(response.statusCode==200){
                                    Looper.prepare()
                                    Toast.makeText(requireContext(),
                                        message, Toast.LENGTH_SHORT).show()
                                    txtNameRegister.setText("")
                                    txtConfirmPasswordRegister.setText("")
                                    txtPasswordRegister.setText("")
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