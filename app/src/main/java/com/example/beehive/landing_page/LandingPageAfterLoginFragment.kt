package com.example.beehive.landing_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.DetailStingFragment
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVBeeworkerAdapter
import com.example.beehive.adapters.RVCategoryAdapter
import com.example.beehive.adapters.RVStingAdapter
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserData
import com.example.beehive.data.*
import com.example.beehive.env
import com.example.beehive.stings.DetailBeeworkerFragment
import com.example.beehive.transaction_sting.DetailOrderedStingFragment
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class LandingPageAfterLoginFragment(
    var listCategory: List<Category>,
    var listBeeworker: List<UserData>
) : Fragment() {

    lateinit var rv: RecyclerView
    lateinit var rvSting: RecyclerView
    lateinit var rvBeeworker: RecyclerView
    lateinit var adpt: RVCategoryAdapter
    lateinit var adptSting: RVStingAdapter
    lateinit var animLoading1: LottieAnimationView
    lateinit var lblName: TextView

    var listSting: List<StingData> = arrayListOf()

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
        return inflater.inflate(R.layout.fragment_landing_page_after_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Beehive"

        lblName = view.findViewById(R.id.lblName)
        animLoading1 = view.findViewById(R.id.animLoading1)
        //init components
        rv = view.findViewById(R.id.rvKategori)
        rvSting = view.findViewById(R.id.rvStingMost)
        rvBeeworker = view.findViewById(R.id.rvBeeworker)

        rv.adapter = RVCategoryAdapter(listCategory){ pos ->
            var key = listCategory[pos].ID_CATEGORY.toString()
            (activity as MainActivity).detailCategory(key,listCategory[pos].NAMA_CATEGORY)
        }
        rv.layoutManager = GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)

        rvBeeworker.adapter = RVBeeworkerAdapter(listBeeworker){
            var beeworker:UserData = listBeeworker[it]
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, DetailBeeworkerFragment(beeworker))
                .commit()
        }
        rvBeeworker.layoutManager = GridLayoutManager(view.context, 1, GridLayoutManager.VERTICAL, false)

        lblName.text = acti.userLogin!!.NAMA.toString()
        //fetch most sting
        val stingMostAPI = ApiConfiguration.getApiService().fetchMostSting()
        stingMostAPI.enqueue(object: Callback<StingDRO> {
            override fun onResponse(call: Call<StingDRO>, response: retrofit2.Response<StingDRO>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        animLoading1.visibility = View.GONE
                        var dataSting = responseBody.data as StingData
//                        listSting.add(dataSting.data)
                        listSting = listOf(dataSting)

                        rvSting.adapter = RVStingAdapter(listSting){
                            var sting:StingData = listSting[it]
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.frMain, DetailStingFragment(sting.ID_STING.toString(),sting,
                                    LandingPageAfterLoginFragment(acti.listCategory,acti.listBeeworker))
                                )
                                .commit()
                        }
                        rvSting.layoutManager = LinearLayoutManager(view.context)
                    }
                }
            }

            override fun onFailure(call: Call<StingDRO>, t: Throwable) {

            }

        })
        //fetch project ongoing

        val ongoing = ApiConfiguration.getApiService().getOngoingTransactionSting(
            remember_token = acti.userLogin!!.REMEMBER_TOKEN.toString()
        )
        ongoing.enqueue(object: Callback<TransactionStingDRO>{
            override fun onResponse(call: Call<TransactionStingDRO>, response: retrofit2.Response<TransactionStingDRO>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        val data: TransactionStingData = responseBody.data!!
                        var progressTotal:Int = responseBody.message.toString().toInt()
                        if(progressTotal>90){
                            progressTotal = 90
                        }
                        var imgOngoing:ImageView = view.findViewById(R.id.imgOngoing)
                        var lblOngoingName:TextView = view.findViewById(R.id.lblOngoingName)
                        var lblOngoingPercent:TextView = view.findViewById(R.id.lblOngoingPercent)
                        var lblOngoingOpenDetail:TextView = view.findViewById(R.id.lblOngoingOpenDetail)
                        var progressBar:ProgressBar = view.findViewById(R.id.progressBar)
                        progressBar.progress = progressTotal
                        lblOngoingPercent.text = "$progressTotal%"
                        lblOngoingName.text = data.sting!!.TITLE_STING
                        if(data.sting!!.TITLE_STING.toString().length>15){
                            lblOngoingName.text = data.sting!!.TITLE_STING.toString().substring(0,14)+"..."
                        }
                        lblOngoingOpenDetail.setOnClickListener {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.frMain, DetailOrderedStingFragment(data.sting.EMAIL_BEEWORKER.toString(),
                                    data.REQUIREMENT_PROJECT.toString(), data.COMMISION.toString(),
                                    data.DATE_START.toString(), data.DATE_END.toString(), data))
                                .commit()
                        }
                        Picasso.get()
                            .load(env.URLIMAGE +"sting-thumbnails/${data.sting.NAMA_THUMBNAIL}")
                            .resize(69,64)
                            .onlyScaleDown()
                            .into(imgOngoing)
                        imgOngoing.clipToOutline = true
                        imgOngoing.setBackgroundResource(R.drawable.rounded_corner_picture_1)
                    }
                }else{
                    //tidak ada yang ongoing
                    var imgOngoing:ImageView = view.findViewById(R.id.imgOngoing)
                    var lblOngoingName:TextView = view.findViewById(R.id.lblOngoingName)
                    var lblOngoingPercent:TextView = view.findViewById(R.id.lblOngoingPercent)
                    var lblOngoingOpenDetail:TextView = view.findViewById(R.id.lblOngoingOpenDetail)
                    var progressBar:ProgressBar = view.findViewById(R.id.progressBar)
                    imgOngoing.visibility = View.GONE
                    lblOngoingPercent.visibility = View.GONE
                    lblOngoingOpenDetail.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    lblOngoingName.text = "There is no ongoing projects"
                    lblOngoingName.setPadding(100,0,0,0)

                }
            }

            override fun onFailure(call: Call<TransactionStingDRO>, t: Throwable) {

            }

        })

    }

}