package com.example.beehive.user_profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.env
import com.squareup.picasso.Picasso

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
            //TODO JB
        }
    }
}