package com.example.beehive.stings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.*
import retrofit2.Call
import retrofit2.Callback
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVStingAdapter
import com.example.beehive.landing_page.LandingPageAfterLoginFragment
import com.example.beehive.landing_page.LandingPageFragment

class DetailCategoryFragment(
    var key: String, var namaCategory:String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    lateinit var rv: RecyclerView
    var listSting: List<StingData> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Stings for $namaCategory"

        var btnBack:ImageButton = view.findViewById(R.id.btnBack)
        var lblTitle:TextView = view.findViewById(R.id.lblTitle)
        lblTitle.text = "Stings for $namaCategory"
        btnBack.setOnClickListener{
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
        var animLoading3 = view.findViewById<LottieAnimationView>(R.id.animLoading3)
        rv = view.findViewById(R.id.rvStingByCategory)

        val categoryAPI = ApiConfiguration.getApiService().fetchStingByCategory(key.toInt())
        categoryAPI.enqueue(object: Callback<ListStingDRO> {
            override fun onResponse(call: Call<ListStingDRO>, response: retrofit2.Response<ListStingDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        animLoading3.visibility = View.GONE
                        listSting = responseBody.data as List<StingData>

                        rv.adapter = RVStingAdapter(listSting) { pos ->
                            var key_id_sting = listSting[pos].ID_STING.toString()
                            acti.detailSting(key_id_sting,listSting[pos],DetailCategoryFragment(key, namaCategory))
                        }
                        rv.layoutManager = LinearLayoutManager(view.context)
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<ListStingDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
                Toast.makeText(requireContext(),
                    "Anda sedang offline", Toast.LENGTH_SHORT).show()
            }
        })
    }

}