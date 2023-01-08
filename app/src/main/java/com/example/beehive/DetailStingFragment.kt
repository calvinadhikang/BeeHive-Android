package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVStingAdapter
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.StingDRO
import com.example.beehive.data.StingData
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class DetailStingFragment(
    var stingId: String
) : Fragment() {

    lateinit var tvWorker: TextView
    lateinit var tvDescription: TextView
    lateinit var tvPrice: TextView
    lateinit var imgWorker: ImageView
    lateinit var imgSting: ImageView
//    lateinit var animLoading1: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()

        //initialize components
        tvWorker = view.findViewById(R.id.tvWorkerDetail)
        tvDescription = view.findViewById(R.id.tvDescriptionDetail)
        tvPrice = view.findViewById(R.id.tvPriceDetail)
        imgWorker = view.findViewById(R.id.imgWorkerDetail)
        imgSting = view.findViewById(R.id.imgStingDetail)

        //run animation loading
//        animLoading1 = view.findViewById(R.id.animLoading1)

        //fetch sting
        val stingMostAPI = ApiConfiguration.getApiService().getStingDetail(stingId)
        stingMostAPI.enqueue(object: Callback<StingDRO> {
            override fun onResponse(call: Call<StingDRO>, response: retrofit2.Response<StingDRO>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
//                        animLoading1.visibility = View.GONE
                        var dataSting = responseBody.data as StingData

                        acti.title = dataSting.TITLE_STING

                        tvWorker.text = dataSting.author!!.NAMA
                        tvDescription.text = dataSting.DESKRIPSI
                        tvPrice.text = "Rp. " + dataSting.PRICE_BASIC

                        //Set WorkerThumbnail
                        Picasso.get()
                            .load(env.URLIMAGE +"profile-pictures/${dataSting.author!!.PICTURE}")
                            .resize(50,50)
                            .into(imgWorker)

                        //Set StingThumbnail
                        Picasso.get()
                            .load(env.URLIMAGE +"sting-thumbnails/${dataSting.NAMA_THUMBNAIL}")
                            .resize(90,150)
                            .onlyScaleDown()
                            .into(imgSting)
                    }
                }
            }

            override fun onFailure(call: Call<StingDRO>, t: Throwable) {

            }

        })
    }

}