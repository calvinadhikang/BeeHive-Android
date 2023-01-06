package com.example.beehive.user_profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beehive.activities.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.ChangePasswordDTO
import com.example.beehive.env
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class UserChangePasswordFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_user_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.title = "Change Password"
        acti.supportActionBar!!.hide()
        val imgUser: ImageView = view.findViewById(R.id.imgUser)
        val txtNewPassword: EditText = view.findViewById(R.id.txtNewPassword)
        val txtConfirmPassword: EditText = view.findViewById(R.id.txtConfirmPassword)
        val btnBackUserProfile: ImageButton = view.findViewById(R.id.btnBackUserProfile)
        val lblNamaUserProfile: TextView = view.findViewById(R.id.lblNamaUserProfile)
        val btnSaveChanges: Button = view.findViewById(R.id.btnSaveChanges)
        try {
            Picasso.get()
                .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
                .resize(100,100)
                .into(imgUser)
            imgUser.clipToOutline = true
            imgUser.setBackgroundResource(R.drawable.full_rounded_picture)
            lblNamaUserProfile.text = acti.userLogin!!.NAMA
        }catch (e:Error){
            Log.e("ERROR_GET_PICTURE",e.message.toString())
        }
        btnBackUserProfile.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserEditProfileFragment())
                .commit()
        }

        btnSaveChanges.setOnClickListener {
            val newPass: String = txtNewPassword.text.toString()
            val confPass: String = txtConfirmPassword.text.toString()
            if(newPass!=confPass||newPass==""){
                acti.showModal("Inputan Tidak Tepat!"){}
                return@setOnClickListener
            }else{
                try {
                    val client = ApiConfiguration.getApiService().changePassword(
                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                        changePasswordData = ChangePasswordDTO(
                            new = newPass,
                            confirm = confPass
                        )
                    )
                    client.enqueue(object: Callback<BasicDRO> {
                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                            val responseBody = response.body()
                            if(response.isSuccessful){
                                if(responseBody!=null){
                                    if(responseBody.data!=null){
                                        val message:String = responseBody.message!!
                                        acti.showModal(message){}
                                    }
                                }
                            }
                            else{
                                val statusCode:Int = response.code()
                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                if(statusCode==403){
                                    acti.showModal("Panjang password minimal 8 karakter!"){}
                                }

                            }
                        }

                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
//                        acti.showModal("You need permission to continue"){}
//                        Log.e("ERRORPIC",selectedImageUri.toString())
//                        Log.e("Error Upload Picture", "onFailure: ${t.message}")
                        }
                    })
                }catch (e:Error){
                    Log.e("NETWORKERROR",e.message.toString())
                    acti.showModal("Network Error!"){}
                }
            }
        }
    }
}