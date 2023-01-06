package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.beehive.api_config.ApiConfiguration
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import com.example.beehive.activities.MainActivity
import com.example.beehive.data.*
import com.example.beehive.transaction_sting.DetailOrderedStingFragment

class NotificationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.show()
        acti.title = "Notifications"

        var listTransaction: ArrayList<TransactionStingData> = ArrayList()
        var daftarTransaction: LVTransactionAdapter
        var listButton: ArrayList<Button> = ArrayList()
        var listViewNotif: ListView = view.findViewById(R.id.listViewTransactionNotif)
        var btnPendingNotif: Button = view.findViewById(R.id.btnPendingNotif)
        var btnAcceptedNotif: Button = view.findViewById(R.id.btnAcceptedNotif)
        var btnRevisionNotif: Button = view.findViewById(R.id.btnRevisionNotif)
        var btnCanceledNotif: Button = view.findViewById(R.id.btnCanceledNotif)
        var btnDoneNotif: Button = view.findViewById(R.id.btnDoneNotif)
        listButton.add(btnPendingNotif)
        listButton.add(btnAcceptedNotif)
        listButton.add(btnRevisionNotif)
        listButton.add(btnCanceledNotif)
        listButton.add(btnDoneNotif)
        for (i in listButton){
            i.setBackgroundColor(resources.getColor(R.color.light_gray))
        }
        btnPendingNotif.setBackgroundColor(resources.getColor(R.color.primary))

        btnPendingNotif.setOnClickListener(){ //Pending Transaction
            for (i in listButton){
                i.setBackgroundColor(resources.getColor(R.color.light_gray))
            }
            btnPendingNotif.setBackgroundColor(resources.getColor(R.color.primary))

            val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
                "0",
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
            )
            client.enqueue(object: Callback<ListTransactionStingDRO> {
                override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                                listTransaction = responseBody.data as ArrayList<TransactionStingData>
                                daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                        namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                        .commit()
                                }, { id ->
                                    val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                        id,
                                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                    )
                                    cancelClient.enqueue(object: Callback<BasicDRO> {
                                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    if(responseBody.data!=null){
                                                        acti.showModal(responseBody.message.toString()){}
                                                    }
                                                }
                                            }
                                            else{
                                                val statusCode:Int = response.code()
                                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                                if(statusCode==404){
                                                    acti.showModal("Order tidak ditemukan"){}
                                                }
                                                else if(statusCode==403){
                                                    acti.showModal("Order ini tidak dalam keadaan pending"){}
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                            Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                        }
                                    })
                                })
                                listViewNotif.adapter = daftarTransaction
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }

        btnAcceptedNotif.setOnClickListener(){ //Accepted Transaction
            for (i in listButton){
                i.setBackgroundColor(resources.getColor(R.color.light_gray))
            }
            btnAcceptedNotif.setBackgroundColor(resources.getColor(R.color.primary))

            val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
                "1",
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
            )
            client.enqueue(object: Callback<ListTransactionStingDRO> {
                override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                                listTransaction = responseBody.data as ArrayList<TransactionStingData>
                                daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                        namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                        .commit()
                                }, { id ->
                                    val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                        id,
                                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                    )
                                    cancelClient.enqueue(object: Callback<BasicDRO> {
                                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    if(responseBody.data!=null){
                                                        acti.showModal(responseBody.message.toString()){}
                                                    }
                                                }
                                            }
                                            else{
                                                val statusCode:Int = response.code()
                                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                                if(statusCode==404){
                                                    acti.showModal("Order tidak ditemukan"){}
                                                }
                                                else if(statusCode==403){
                                                    acti.showModal("Order ini tidak dalam keadaan pending"){}
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                            Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                        }
                                    })
                                })
                                listViewNotif.adapter = daftarTransaction
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }

        btnDoneNotif.setOnClickListener(){ //Done Transaction
            for (i in listButton){
                i.setBackgroundColor(resources.getColor(R.color.light_gray))
            }
            btnDoneNotif.setBackgroundColor(resources.getColor(R.color.primary))

            val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
                "3",
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
            )
            client.enqueue(object: Callback<ListTransactionStingDRO> {
                override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                                listTransaction = responseBody.data as ArrayList<TransactionStingData>
                                daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                        namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                        .commit()
                                }, { id ->
                                    val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                        id,
                                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                    )
                                    cancelClient.enqueue(object: Callback<BasicDRO> {
                                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    if(responseBody.data!=null){
                                                        acti.showModal(responseBody.message.toString()){}
                                                    }
                                                }
                                            }
                                            else{
                                                val statusCode:Int = response.code()
                                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                                if(statusCode==404){
                                                    acti.showModal("Order tidak ditemukan"){}
                                                }
                                                else if(statusCode==403){
                                                    acti.showModal("Order ini tidak dalam keadaan pending"){}
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                            Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                        }
                                    })
                                })
                                listViewNotif.adapter = daftarTransaction
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }

        btnRevisionNotif.setOnClickListener(){ //Done Transaction
            for (i in listButton){
                i.setBackgroundColor(resources.getColor(R.color.light_gray))
            }
            btnRevisionNotif.setBackgroundColor(resources.getColor(R.color.primary))

            val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
                "2",
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
            )
            client.enqueue(object: Callback<ListTransactionStingDRO> {
                override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                                listTransaction = responseBody.data as ArrayList<TransactionStingData>
                                daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                        namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                        .commit()
                                }, { id ->
                                    val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                        id,
                                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                    )
                                    cancelClient.enqueue(object: Callback<BasicDRO> {
                                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    if(responseBody.data!=null){
                                                        acti.showModal(responseBody.message.toString()){}
                                                    }
                                                }
                                            }
                                            else{
                                                val statusCode:Int = response.code()
                                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                                if(statusCode==404){
                                                    acti.showModal("Order tidak ditemukan"){}
                                                }
                                                else if(statusCode==403){
                                                    acti.showModal("Order ini tidak dalam keadaan pending"){}
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                            Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                        }
                                    })
                                })
                                listViewNotif.adapter = daftarTransaction
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }

        btnCanceledNotif.setOnClickListener(){ //Done Transaction
            for (i in listButton){
                i.setBackgroundColor(resources.getColor(R.color.light_gray))
            }
            btnCanceledNotif.setBackgroundColor(resources.getColor(R.color.primary))

            val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
                "-2",
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
            )
            client.enqueue(object: Callback<ListTransactionStingDRO> {
                override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                                listTransaction = responseBody.data as ArrayList<TransactionStingData>
                                daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                        namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                        .commit()
                                }, { id ->
                                    val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                        id,
                                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                    )
                                    cancelClient.enqueue(object: Callback<BasicDRO> {
                                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    if(responseBody.data!=null){
                                                        acti.showModal(responseBody.message.toString()){}
                                                    }
                                                }
                                            }
                                            else{
                                                val statusCode:Int = response.code()
                                                Log.e("ErrorLogin", "Fail Access: $statusCode")
                                                if(statusCode==404){
                                                    acti.showModal("Order tidak ditemukan"){}
                                                }
                                                else if(statusCode==403){
                                                    acti.showModal("Order ini tidak dalam keadaan pending"){}
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                            Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                        }
                                    })
                                })
                                listViewNotif.adapter = daftarTransaction
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }

        val client = ApiConfiguration.getApiService().fetchTransactionStingByStatus(
            "0",
            remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
        )
        client.enqueue(object: Callback<ListTransactionStingDRO> {
            override fun onResponse(call: Call<ListTransactionStingDRO>, response: retrofit2.Response<ListTransactionStingDRO>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.data!=null){
                            var data: List<TransactionStingData> = responseBody.data as List<TransactionStingData>
                            listTransaction = responseBody.data as ArrayList<TransactionStingData>
                            daftarTransaction = LVTransactionAdapter(requireActivity(), listTransaction, acti.userLogin!!, {
                                    namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans ->
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frMain, DetailOrderedStingFragment(namaBeeworker, requirement, harga, tglMulai, tglSelesai, trans))
                                    .commit()
                            }, { id ->
                                val cancelClient = ApiConfiguration.getApiService().cancelTransactionSting(
                                    id,
                                    remember_token = acti.userLogin!!.REMEMBER_TOKEN!!
                                )
                                cancelClient.enqueue(object: Callback<BasicDRO> {
                                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                                        if(response.isSuccessful){
                                            val responseBody = response.body()
                                            if(responseBody!=null){
                                                if(responseBody.data!=null){
                                                    acti.showModal(responseBody.message.toString()){}
                                                }
                                            }
                                        }
                                        else{
                                            val statusCode:Int = response.code()
                                            Log.e("ErrorLogin", "Fail Access: $statusCode")
                                            if(statusCode==404){
                                                acti.showModal("Order tidak ditemukan"){}
                                            }
                                            else if(statusCode==403){
                                                acti.showModal("Order ini tidak dalam keadaan pending"){}
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                                        Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                                    }
                                })
                            })
                            listViewNotif.adapter = daftarTransaction
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ListTransactionStingDRO>, t: Throwable) {
                Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
            }
        })


    }

}