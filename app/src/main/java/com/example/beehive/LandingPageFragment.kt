package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LandingPageFragment : Fragment() {

    lateinit var rv: RecyclerView
    lateinit var rvSting: RecyclerView
    lateinit var adpt: RVCategoryAdapter
    lateinit var adptSting: RVStingAdapter

    var listCategory: List<Category> = listOf()
    var listSting: ArrayList<StingMost> = arrayListOf()

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
        acti.supportActionBar!!.show()
        acti.title = "Beehive"

        //init components
        rv = view.findViewById(R.id.rvKategori)
        rvSting = view.findViewById(R.id.rvStingMost)

        //fetchCategory
        val categoryAPI = ApiConfiguration.getApiService().fetchCategoryNoAuth()
        categoryAPI.enqueue(object: Callback<ListCategoryDRO> {
            override fun onResponse(call: Call<ListCategoryDRO>, response: retrofit2.Response<ListCategoryDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listCategory = responseBody.data as List<Category>

                        rv.adapter = RVCategoryAdapter(listCategory, { pos ->
                            var key = listCategory[pos].ID_CATEGORY.toString()
                            (activity as MainActivity).detailCategory(key)
                        })
                        rv.layoutManager = GridLayoutManager(view.context, 1, GridLayoutManager.HORIZONTAL, false)
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<ListCategoryDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
            }

        })

        //fetch most sting
        val stingMostAPI = ApiConfiguration.getApiService().fetchMostSting()
        stingMostAPI.enqueue(object: Callback<MostStingDRO>{
            override fun onResponse(call: Call<MostStingDRO>, response: retrofit2.Response<MostStingDRO>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        var dataSting = responseBody.data as StingMost
                        listSting.add(dataSting)

                        rvSting.adapter = RVStingAdapter(listSting)
                        rvSting.layoutManager = LinearLayoutManager(view.context)
                    }
                }
            }

            override fun onFailure(call: Call<MostStingDRO>, t: Throwable) {

            }

        })
    }

}