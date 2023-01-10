package com.example.beehive.landing_page

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVBeeworkerAdapter
import com.example.beehive.adapters.RVCategoryAdapter
import com.example.beehive.adapters.RVStingAdapter
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserData
import com.example.beehive.data.*
import com.example.beehive.stings.DetailBeeworkerFragment
import com.example.beehive.user_auth.UserBeforeLoginFragment
import retrofit2.Call
import retrofit2.Callback

class LandingPageFragment(
    var listCategory: List<Category>,
    var listBeeworker: List<UserData>
) : Fragment() {

    lateinit var rv: RecyclerView
    lateinit var rvSting: RecyclerView
    lateinit var adpt: RVCategoryAdapter
    lateinit var adptSting: RVStingAdapter
    lateinit var animLoading1: LottieAnimationView
    lateinit var lblToLogin: TextView
    lateinit var rvBeeworker: RecyclerView
    lateinit var edtSearch: EditText
//    var listCategory: List<Category> = listOf()
    var listSting: List<StingData> = arrayListOf()

    //ini harus di override bila ada error :
    // -> "Can not perform this action after onSaveInstanceState"
    // dengan cara menghilangkan call kepada : super()
    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
//        acti.title = "Beehive"
        lblToLogin = view.findViewById(R.id.lblToLogin)
        acti.title = "Beehive"
        animLoading1 = view.findViewById(R.id.animLoading1)
        //init components
        rv = view.findViewById(R.id.rvKategori)
        rvSting = view.findViewById(R.id.rvStingMost)

        rvBeeworker = view.findViewById(R.id.rvBeeworker)
        rvBeeworker.adapter = RVBeeworkerAdapter(listBeeworker){
            var beeworker:UserData = listBeeworker[it]
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, DetailBeeworkerFragment(beeworker))
                .commit()
        }
        rvBeeworker.layoutManager = GridLayoutManager(view.context, 1, GridLayoutManager.VERTICAL, false)

        rv.adapter = RVCategoryAdapter(listCategory){ pos ->
            var key = listCategory[pos].ID_CATEGORY.toString()
            (activity as MainActivity).detailCategory(key,listCategory[pos].NAMA_CATEGORY)
        }
        rv.layoutManager = GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)
        lblToLogin.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserBeforeLoginFragment())
                .commit()
        }

        //fetch most sting
        val stingMostAPI = ApiConfiguration.getApiService().fetchMostSting()
        stingMostAPI.enqueue(object: Callback<StingDRO>{
            override fun onResponse(call: Call<StingDRO>, response: retrofit2.Response<StingDRO>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        animLoading1.visibility = View.GONE
                        var dataSting = responseBody.data as StingData
                        listSting = listOf(dataSting)

                        rvSting.adapter = RVStingAdapter(listSting){}
                        rvSting.layoutManager = LinearLayoutManager(view.context)
                    }
                }
            }

            override fun onFailure(call: Call<StingDRO>, t: Throwable) {

            }
        })

        //initialize edittext Search
        edtSearch = view.findViewById(R.id.edtSearchLanding)
        edtSearch.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER){
                var key = edtSearch.text.toString()
                (activity as MainActivity).search(key)
                return@setOnKeyListener true
            }
            false
        }
    }
}
