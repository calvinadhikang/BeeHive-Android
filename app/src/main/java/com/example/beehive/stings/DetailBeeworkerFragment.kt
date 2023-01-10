package com.example.beehive.stings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.DetailStingFragment
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVStingAdapter
import com.example.beehive.api_config.UserData
import com.example.beehive.data.StingData
import com.example.beehive.env
import com.example.beehive.landing_page.LandingPageAfterLoginFragment
import com.example.beehive.landing_page.LandingPageFragment
import com.squareup.picasso.Picasso

class DetailBeeworkerFragment(
    var beeworker: UserData
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
        return inflater.inflate(R.layout.fragment_detail_beeworker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Beeworker's profile"

        var btnBack:ImageButton = view.findViewById(R.id.btnBackUserProfile)
        var btnContactme:Button = view.findViewById(R.id.btnContactme)
        var imgBeeworker:ImageView = view.findViewById(R.id.imgBeeworker)
        var lblBeeworkerName:TextView = view.findViewById(R.id.lblBeeworkerName)
        var lblRatingReview:TextView = view.findViewById(R.id.lblRatingReview)
        var lblBio:TextView = view.findViewById(R.id.lblBio)
        var rvStings:RecyclerView = view.findViewById(R.id.rvStings)

        Picasso.get()
            .load(env.URLIMAGE+"profile-pictures/${beeworker.PICTURE}")
            .fit()
            .into(imgBeeworker)
        imgBeeworker.clipToOutline = true
        imgBeeworker.setBackgroundResource(R.drawable.full_rounded_picture)

        btnBack.setOnClickListener {
            if(acti.isLogin){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain, LandingPageAfterLoginFragment(acti.listCategory, acti.listBeeworker))
                    .commit()
            }else{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain, LandingPageFragment(acti.listCategory,acti.listBeeworker))
                    .commit()
            }
        }
        btnContactme.setOnClickListener {
            //intent email
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                    acti.userLogin!!.EMAIL,null))
            val subject:String = "Beehive"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(beeworker.EMAIL))
            intent.putExtra(Intent.EXTRA_SUBJECT,subject)
            val body:String = "Selamat pagi, saya mau bertanya..."
            intent.putExtra(Intent.EXTRA_TEXT,body)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent,"Open to email"))
        }
        lblBeeworkerName.text = beeworker.NAMA
        lblRatingReview.text = "${beeworker.RATING}(${beeworker.jumlahOrderDone} reviews)"
        lblBio.text = beeworker.BIO

        for (i in 0 until beeworker.stings!!.size){
            beeworker.stings!![i].author = beeworker
        }
        rvStings.adapter = RVStingAdapter(beeworker.stings!!){
            var sting:StingData = beeworker.stings!![it]
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, DetailStingFragment(sting.ID_STING.toString(),sting,DetailBeeworkerFragment(beeworker)))
                .commit()
        }
        rvStings.layoutManager = LinearLayoutManager(view.context)
    }
}