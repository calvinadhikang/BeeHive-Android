package com.example.beehive.lelang_sting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.Category
import com.example.beehive.data.ListCategoryDRO
import com.example.beehive.user_auth.RegisterFinalFragment
import com.example.beehive.user_auth.RegisterFragment
import retrofit2.Call
import retrofit2.Callback

class CreateLelangStingFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_create_lelang_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        val btnNavListLelangSting: Button = view.findViewById(R.id.btnNavListLelangSting)
        val spinnerCategory: Spinner = view.findViewById(R.id.spinnerCategory)
        var listCategory:List<Category> = listOf()
//        var spinnerAdapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
//            requireContext(),android.R.layout.simple_spinner_item, listCategory
//        )
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerCategory.adapter = spinnerAdapter

        btnNavListLelangSting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListLelangStingFragment())
                .commit()
        }
        val client = ApiConfiguration.getApiService().fetchCategory(remember_token = acti.userLogin!!.REMEMBER_TOKEN!!)
        client.enqueue(object: Callback<ListCategoryDRO> {
            override fun onResponse(call: Call<ListCategoryDRO>, response: retrofit2.Response<ListCategoryDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listCategory = responseBody.data as List<Category>
                        var spinnerAdapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
                            requireContext(),android.R.layout.simple_spinner_item, listCategory
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerCategory.adapter = spinnerAdapter
                    }
                }
                else{
                    val statusCode:Int = response.code()
                    val message:String = response.body()!!.message!!
                    Log.e("ERROR FETCH CATEGORY", "Fail Access: $statusCode")
//                    if(statusCode==401){
//                    }
                    Toast.makeText(requireContext(),
                        message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListCategoryDRO>, t: Throwable) {
                Log.e("ERROR FETCH CATEGORY", "onFailure: ${t.message}")
            }

        })

    }
}