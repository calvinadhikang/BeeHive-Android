package com.example.beehive.user_profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.activities.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.env
import com.example.beehive.utils.URIPathHelper
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback

class UserChangePictureFragment : Fragment() {

    lateinit var uploadFileZone: ConstraintLayout
    lateinit var  lblParamUpload: TextView
    lateinit var  btnBackUserProfile: ImageButton
    lateinit var  btnSavePicture: Button
    lateinit var  animLoading4: LottieAnimationView
    lateinit var  imgUser: ImageView
    lateinit var  imgTemp: ImageView
    lateinit var selectedImageUri:Uri

    lateinit var imagename: MultipartBody.Part
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

        animLoading4 = view.findViewById(R.id.animLoading4)
        uploadFileZone = view.findViewById(R.id.uploadFileZone)
        lblParamUpload = view.findViewById(R.id.lblParamUpload)
        btnBackUserProfile = view.findViewById(R.id.btnBackUserProfile)
        btnSavePicture = view.findViewById(R.id.btnSavePicture)
        imgUser = view.findViewById(R.id.imgUser)
        imgTemp = view.findViewById(R.id.imgTemp)

        animLoading4.visibility = View.GONE
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
            if(ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            {
                checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    101)
            }
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
        btnSavePicture.setOnClickListener {
            if(selectedImageUri==null){
                acti.showModal("Anda harus memilih gambar!"){}
                return@setOnClickListener
            }
            animLoading4.visibility = View.VISIBLE

            try {
                val client = ApiConfiguration.getApiService().changePicture(
                    remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                    picture = imagename
                )
                client.enqueue(object: Callback<BasicDRO> {
                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                        val responseBody = response.body()
                        if(response.isSuccessful){
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                        val message:String = responseBody.message!!
                                        acti.userLogin!!.PICTURE = message

                                        Picasso.get()
                                            .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
                                            .resize(80,80)
                                            .into(imgUser)
                                        imgUser.clipToOutline = true
                                        imgUser.setBackgroundResource(R.drawable.full_rounded_picture)
                                    acti.showModal("Sukses mengganti profile picture!"){}
                                    animLoading4.visibility = View.GONE
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("ErrorLogin", "Fail Access: $statusCode")
                            if(statusCode==400){
                                acti.showModal("Picture not found"){}
                            }

                        }
                    }

                    override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                        acti.showModal("You need permission to continue"){}
                        Log.e("ERRORPIC",selectedImageUri.toString())
                        Log.e("Error Upload Picture", "onFailure: ${t.message}")
                    }
                })
            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                acti.showModal("Network Error!"){}
            }

        }
    }
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(),
                permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(permission), requestCode)
        } else {
            Toast.makeText(requireContext(),
                "Permission already granted",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode,
            permissions, grantResults)
         if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    lblParamUpload.text = "File Choosed!"
                    selectedImageUri = data?.data!!
                    imgTemp.setImageURI(selectedImageUri)
                    lblParamUpload.setTextColor(resources.getColor(R.color.success))
                    val uriPathHelper = URIPathHelper()
                    val filePath = uriPathHelper.getPath(requireContext(), selectedImageUri)
                    var file:File = File("${filePath}")
                    val requestBody = file.asRequestBody("multipart".toMediaTypeOrNull())
                    imagename = MultipartBody.Part.createFormData("picture",file.name,requestBody)
                    Log.i("IMAGENAME",data.data.toString())

                }
            }
        }
    }

}