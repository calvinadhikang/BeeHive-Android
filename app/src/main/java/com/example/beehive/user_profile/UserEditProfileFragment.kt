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
import com.example.beehive.env
import com.squareup.picasso.Picasso

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
        val txtName:EditText = view.findViewById(R.id.txtName)
        val txtBio:EditText = view.findViewById(R.id.txtBio)
        val btnSaveChanges:Button = view.findViewById(R.id.btnSaveChanges)
        val btnChangePassword:Button = view.findViewById(R.id.btnChangePassword)
        val btnBackUserProfile:ImageButton = view.findViewById(R.id.btnBackUserProfile)

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
//            etst
            //TODO JB panggil api buat change profile dan refresh data user yg lg login
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