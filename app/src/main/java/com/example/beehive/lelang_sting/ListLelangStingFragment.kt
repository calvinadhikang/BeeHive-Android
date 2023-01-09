package com.example.beehive.lelang_sting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.beehive.activities.MainActivity
import com.example.beehive.R
import com.example.beehive.adapters.RVListLelangSting
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.LelangStingData
import com.example.beehive.data.ListLelangStingDRO
import retrofit2.Call
import retrofit2.Callback

class ListLelangStingFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_list_lelang_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.show()
        acti.title = "List Lelang Sting"

        val btnNavCreateLelangSting: Button = view.findViewById(R.id.btnNavCreateLelangSting)
        val rvListLelangSting: RecyclerView = view.findViewById(R.id.rvListLelangSting)
        val animLoading: LottieAnimationView = view.findViewById(R.id.animLoading)

        lateinit var adapter: RVListLelangSting

        val client = ApiConfiguration.getApiService().fetchLelangSting(
            remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
        )
        client.enqueue(object: Callback<ListLelangStingDRO> {
            override fun onResponse(call: Call<ListLelangStingDRO>, response: retrofit2.Response<ListLelangStingDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.data!=null){
                            animLoading.visibility = View.GONE
                            val listLelang:List<LelangStingData> = (responseBody.data as List<LelangStingData>?)!!
                            adapter = RVListLelangSting(listLelang){
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frMain,
                                        DetailLelangStingFragment(it)
                                    )
                                    .commit()
                            }
                            rvListLelangSting.adapter = adapter
                            rvListLelangSting.layoutManager =
                                GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
                        }
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    Log.e("ERROR FETCH", "Fail Access: $statusCode")
                    if(statusCode==401){
                        acti.showModal("Unauthorized"){}
                    }
                }
            }

            override fun onFailure(call: Call<ListLelangStingDRO>, t: Throwable) {
                Log.e("ERROR FETCH", "onFailure: ${t.message}")
                acti.showModal("Poor network connection detected"){}
            }

        })

        btnNavCreateLelangSting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, CreateLelangStingFragment())
                .commit()
        }
    }
}