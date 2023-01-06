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
import com.example.beehive.data.ChangeProfileUserDTO
import com.example.beehive.env
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class UserEditProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.title = "Edit Profile"
        acti.supportActionBar!!.hide()

        val imgUser:ImageView = view.findViewById(R.id.imgUser)
        val lblNamaUser:TextView = view.findViewById(R.id.lblNamaUser)
        val txtName:EditText = view.findViewById(R.id.txtName)
        val txtBio:EditText = view.findViewById(R.id.txtBio)
        val btnSaveChanges:Button = view.findViewById(R.id.btnSaveChanges)
        val btnChangePassword:Button = view.findViewById(R.id.btnChangePassword)
        val btnBackUserProfile:ImageButton = view.findViewById(R.id.btnBackUserProfile)

        lblNamaUser.text=acti.userLogin!!.NAMA
        txtName.setText(acti.userLogin!!.NAMA)
        txtBio.setText(acti.userLogin!!.BIO)
        try {
            Picasso.get()
                .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
                .resize(100,100)
                .into(imgUser)
            imgUser.clipToOutline = true
            imgUser.setBackgroundResource(R.drawable.full_rounded_picture)
        }catch (e:Error){
            Log.e("ERROR_GET_PICTURE",e.message.toString())
        }

        btnSaveChanges.setOnClickListener {
            try {
                val client = ApiConfiguration.getApiService().changeProfile(
                    remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                    changeProfileData = ChangeProfileUserDTO(
                        name = txtName.text.toString(),
                        bio = txtBio.text.toString()
                    )
                )
                client.enqueue(object: Callback<BasicDRO> {
                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                        val responseBody = response.body()
                        if(response.isSuccessful){
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                    acti.userLogin!!.NAMA = txtName.text.toString()
                                    acti.userLogin!!.BIO = txtBio.text.toString()
                                    Log.d("TAG", responseBody.data.toString())
                                    lblNamaUser.text=acti.userLogin!!.NAMA
                                    txtName.setText(acti.userLogin!!.NAMA)
                                    txtBio.setText(acti.userLogin!!.BIO)
                                    acti.showModal(responseBody.message!!.toString()){}
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("ErrorLogin", "Fail Access: $statusCode")
                            if(statusCode==400){
                                acti.showModal("User not found"){}
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
        btnBackUserProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserProfileFragment())
                .commit()
        }
        imgUser.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserChangePictureFragment())
                .commit()
        }
        btnChangePassword.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserChangePasswordFragment())
                .commit()
        }
    }

}