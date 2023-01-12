package com.example.beehive

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.activities.MainActivity
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.BuyStingDTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetFragment(
    var judul: String,
    var tipe: String,
    var harga: String,
    var nama: String,
    var tgl: String,
    var stingId: String,
    var req: String
): BottomSheetDialogFragment (){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Notifications"
        var txtNamaBilling: TextView = view.findViewById(R.id.txtNamaBilling)
        var txtStingBilling: TextView = view.findViewById(R.id.txtStingBilling)
        var txtTipeBilling: TextView = view.findViewById(R.id.txtTipeBilling)
        var txtTanggalBilling: TextView = view.findViewById(R.id.txtTanggalBilling)
        var txtNominalBilling: TextView = view.findViewById(R.id.txtNominalBilling)
        var txtSubtotalBottom: TextView = view.findViewById(R.id.txtSubtotalBottom)
        var btnBuyBottom: Button = view.findViewById(R.id.btnBuyBottom)

        txtNamaBilling.text = nama
        txtStingBilling.text = judul
        txtTipeBilling.text = tipe
        txtTanggalBilling.text = tgl
        txtNominalBilling.text = harga.toInt().toRupiah()
        txtSubtotalBottom.text = harga.toInt().toRupiah()

        var mode = if (tipe == "Pro") "premium" else "basic"

        btnBuyBottom.setOnClickListener(){
            val client = ApiConfiguration.getApiService().buySting(
                stingId,
                mode,
                remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                BuyStingDTO(req)
            )
            client.enqueue(object: Callback<BasicDRO> {
                override fun onResponse(call: Call<BasicDRO>, response: Response<BasicDRO>){
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            if(responseBody.data!=null){
                                acti.showModal("Berhasil membuat order sting!"){
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, NotificationFragment())
                                        .commit()
                                }
                            }
                        }
                    }
                    else{
                        val statusCode:Int = response.code()
                        Log.e("ErrorLogin", "Fail Access: $statusCode")
                        if(statusCode==400){
                            acti.showModal("Anda harus mengisi requirement project!"){}
                        }
                        else if(statusCode==412){
                            acti.showModal("Credit anda tidak cukup"){}
                        }
                        else if(statusCode==409){
                            acti.showModal("Anda sedang memesan sting ini!"){}
                        }
                    }
                }

                override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                    Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                }
            })
        }
    }

}