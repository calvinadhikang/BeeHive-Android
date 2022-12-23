package com.example.beehive.lelang_sting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.Category
import com.example.beehive.data.CreateLelangStingDTO
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
        acti.supportActionBar!!.show()
        acti.title = "Create Lelang Sting"
        val txtProjectRequirement: EditText = view.findViewById(R.id.txtProjectRequirement)
        val txtProjectTitle: EditText = view.findViewById(R.id.txtProjectTitle)
        val txtProjectCommision: EditText = view.findViewById(R.id.txtProjectCommision)

        val btnNavListLelangSting: Button = view.findViewById(R.id.btnNavListLelangSting)
        val btnCreateLelangSting: Button = view.findViewById(R.id.btnCreateLelangSting)
        val spinnerCategory: Spinner = view.findViewById(R.id.spinnerCategory)
        var token:String = acti.userLogin!!.REMEMBER_TOKEN!!
//        var spinnerAdapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
//            requireContext(),android.R.layout.simple_spinner_item, listCategory
//        )
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerCategory.adapter = spinnerAdapter

        var spinnerAdapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
            requireContext(),android.R.layout.simple_spinner_item, acti.listCategory
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = spinnerAdapter


        btnNavListLelangSting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListLelangStingFragment())
                .commit()
        }


        btnCreateLelangSting.setOnClickListener {
            var title:String = txtProjectTitle.text.toString()
            var requirement:String = txtProjectRequirement.text.toString()
            var priceStr:String = txtProjectCommision.text.toString()
            var price:Int = 0
            if(title==""||requirement==""||priceStr==""){
                acti.showModal("Semua input harus diisi"){}
                return@setOnClickListener
            }
            price = priceStr.toInt()
            var category:Int = acti.listCategory[spinnerCategory.selectedItemPosition].ID_CATEGORY!!
            var lelangData:CreateLelangStingDTO = CreateLelangStingDTO(title,requirement, price, category)
            try {
                val client = ApiConfiguration.getApiService().CreateLelangSting(lelangStingData = lelangData,
                    remember_token = token)
                client.enqueue(object: Callback<BasicDRO> {
                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                        val responseBody = response.body()
                        if(response.isSuccessful){
                            if(responseBody!=null){
                                acti.showModal("Berhasil membuat lelang sting baru!"){}
                                txtProjectTitle.setText("")
                                txtProjectRequirement.setText("")
                                txtProjectCommision.setText("")
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("CREATELELANGSTINGERROR", "Fail Access: $statusCode")
                            if(statusCode==400){
                                acti.showModal("Semua input harus diisi!"){}
                            }
                            else if(statusCode==401){
                                acti.showModal("Unauthorized"){}
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                        acti.showModal("Poor network connection detected"){}
                        Log.e("CREATELELANGSTINGERROR", "onFailure: ${t.message}")
                    }
                })
            }catch (e:Error){
                Log.e("NETWORKERROR",e.message.toString())
                acti.showModal("Network Error!"){}
            }
        }
    }
}