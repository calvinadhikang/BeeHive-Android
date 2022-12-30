package com.example.beehive.user_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.env
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
        acti.title = "User Profile"
        acti.supportActionBar!!.show()
        acti.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val lblNamaUserProfile:TextView = view.findViewById(R.id.lblNamaUserProfile)
        val lblBalanceUserProfile:TextView = view.findViewById(R.id.lblBalanceUserProfile)
        val lblBirthUserProfile:TextView = view.findViewById(R.id.lblBirthUserProfile)
        val lblAccountTypeUserProfile:TextView = view.findViewById(R.id.lblAccountTypeUserProfile)
        val lblEmailUserProfile:TextView = view.findViewById(R.id.lblEmailUserProfile)
        val imgProfilePicture:ImageView = view.findViewById(R.id.imageProfilePictureUserProfile)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnTopUp: Button = view.findViewById(R.id.btnTopUp)
        val btnEditProfile: Button = view.findViewById(R.id.btnEditProfileUser)

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
        imgProfilePicture.clipToOutline = true
        imgProfilePicture.setBackgroundResource(R.drawable.full_rounded_picture)


        btnLogout.setOnClickListener {
            acti.showConfirmation("Are you sure you want to logout?","Yes",
            "No",{
                    acti.logout()
                    acti.showModal("Berhasil Log Out!"){}
                 },{

                })
        }
        btnEditProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserEditProfileFragment())
                .commit()
        }
        btnTopUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, TopUpFragment())
                .commit()
        }

    }

}