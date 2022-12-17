package com.example.beehive.user_profile

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.data.BasicDRO
import com.example.beehive.env
import com.example.beehive.user_auth.RegisterFinalFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import com.squareup.picasso.Picasso;

class UserProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        val lblNamaUserProfile:TextView = view.findViewById(R.id.lblNamaUserProfile)
        val lblBalanceUserProfile:TextView = view.findViewById(R.id.lblBalanceUserProfile)
        val lblBirthUserProfile:TextView = view.findViewById(R.id.lblBirthUserProfile)
        val lblAccountTypeUserProfile:TextView = view.findViewById(R.id.lblAccountTypeUserProfile)
        val lblEmailUserProfile:TextView = view.findViewById(R.id.lblEmailUserProfile)
        val imgProfilePicture:ImageView = view.findViewById(R.id.imageProfilePictureUserProfile)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        lblNamaUserProfile.text = acti.userLogin!!.NAMA
        lblBalanceUserProfile.text = acti.userLogin!!.BALANCE!!.toBigDecimal().toInt().toRupiah()
        lblBirthUserProfile.text = acti.userLogin!!.TANGGAL_LAHIR
        if(acti.userLogin!!.SUBSCRIBED==0){
            lblAccountTypeUserProfile.text = "Free User"
        }else{
            lblAccountTypeUserProfile.text = "Subscribed"
        }
        lblEmailUserProfile.text = acti.userLogin!!.EMAIL

        //ini untuk load image lewat api
        Picasso.get()
            .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
            .resize(50,50)
            .into(imgProfilePicture)

//        val client = ApiConfiguration.getApiService().getProfile(remember_token = acti.userLogin!!.REMEMBER_TOKEN!!)
//        client.enqueue(object: Callback<UserDRO> {
//            override fun onResponse(call: Call<UserDRO>, response: retrofit2.Response<UserDRO>){
//                if(response.isSuccessful){
//                    val responseBody = response.body()
//                    if(responseBody!=null){
//                        if(responseBody.data!=null){
//                            var data:UserData = responseBody.data
//                            lblNamaUserProfile.text = data.NAMA
//                            lblBalanceUserProfile.text = data.BALANCE!!.toBigDecimal().toInt().toRupiah()
//                            lblBirthUserProfile.text = data.TANGGAL_LAHIR
//                            if(data.SUBSCRIBED==0){
//                                lblAccountTypeUserProfile.text = "Free User"
//                            }else{
//                                lblAccountTypeUserProfile.text = "Subscribed"
//                            }
//                            lblEmailUserProfile.text = data.EMAIL
//                            Picasso.get().load(env.URLIMAGE+"profile-pictures/${data.PICTURE}").into(imgProfilePicture)
//                        }
//                    }
//                }
//                else{
//                    val statusCode:Int = response.code()
//                    Log.e("GET_PROFILE_ERROR", "Fail Access: $statusCode")
//                    if(statusCode==401){
//                        Toast.makeText(requireContext(),
//                            "Unauthorized", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<UserDRO>, t: Throwable) {
//                Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
//            }
//
//        })
        btnLogout.setOnClickListener {
            acti.logout()

            //todo berikan konfirmasi
            Toast.makeText(requireContext(),
                "Berhasil Logout", Toast.LENGTH_SHORT).show()
        }

    }
}