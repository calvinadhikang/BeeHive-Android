package com.example.beehive.user_profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.env
import com.squareup.picasso.Picasso

class UserChangePictureFragment : Fragment() {

    lateinit var uploadFileZone: ConstraintLayout
    lateinit var  lblParamUpload: TextView
    lateinit var  btnBackUserProfile: ImageButton
    lateinit var  imgUser: ImageView
    lateinit var selectedImageUri:Uri
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
        return inflater.inflate(R.layout.fragment_user_change_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.title = "Change Profile Picture"
        acti.supportActionBar!!.hide()

        uploadFileZone = view.findViewById(R.id.uploadFileZone)
        lblParamUpload = view.findViewById(R.id.lblParamUpload)
        btnBackUserProfile = view.findViewById(R.id.btnBackUserProfile)
        imgUser = view.findViewById(R.id.imgUser)

        try {
            Picasso.get()
                .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
                .resize(80,80)
                .into(imgUser)
            imgUser.clipToOutline = true
            imgUser.setBackgroundResource(R.drawable.full_rounded_picture)
        }catch (e:Error){
            Log.e("ERROR_GET_PICTURE",e.message.toString())
        }

        uploadFileZone.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it, 101)

            }
        }
        btnBackUserProfile.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserEditProfileFragment())
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    lblParamUpload.text = "File Choosed!"
                    selectedImageUri = data?.data!!
                    imgUser.setImageURI(selectedImageUri)
                    lblParamUpload.setTextColor(resources.getColor(R.color.success))
                }
            }
        }
    }

}