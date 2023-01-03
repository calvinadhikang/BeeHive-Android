package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.*
import retrofit2.Call
import retrofit2.Callback
import com.example.beehive.activities.MainActivity

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
        acti.supportActionBar!!.show()
        acti.title = "Stings for $namaCategory"

        rv = view.findViewById(R.id.rvStingByCategory)

        val categoryAPI = ApiConfiguration.getApiService().fetchStingByCategory(key.toInt())
        categoryAPI.enqueue(object: Callback<ListStingDRO> {
            override fun onResponse(call: Call<ListStingDRO>, response: retrofit2.Response<ListStingDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listSting = responseBody.data as List<StingData>

                        rv.adapter = RVStingAdapter(listSting)
                        rv.layoutManager = LinearLayoutManager(view.context)
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<ListStingDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
            }
        })
    }
}