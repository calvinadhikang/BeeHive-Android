package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.api_config.UserDRO
import com.example.beehive.api_config.UserData
import com.example.beehive.data.StingDRO
import com.example.beehive.data.StingData
import com.example.beehive.user_auth.LoginFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import com.example.beehive.activities.MainActivity

class DetailBuyStingFragment(
    var stingId: String
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_buy_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.show()
        acti.title = "Buy Sting"

        var txtNamaDetail: TextView = view.findViewById(R.id.txtNamaDetail)
        var txtBalanceDetail: TextView = view.findViewById(R.id.txtBalanceDetail)
        var txtNamaAuthorDetail: TextView = view.findViewById(R.id.txtNamaAuthorDetail)
        var txtRatingDetail: TextView = view.findViewById(R.id.txtRatingDetail)
        var txtJumlahReviewDetail: TextView = view.findViewById(R.id.txtJumlahReviewDetail)
        var txtDescProDetail: TextView = view.findViewById(R.id.txtDescProDetail)
        var txtHargaProDetail: TextView = view.findViewById(R.id.txtHargaProDetail)
        var txtDescBasicDetail: TextView = view.findViewById(R.id.txtDescBasicDetail)
        var txtHargaBasixDetail: TextView = view.findViewById(R.id.txtHargaBasixDetail)
        var btnBuyProDetail: Button = view.findViewById(R.id.btnBuyProDetail)
        var btnBuyBasicDetail: Button = view.findViewById(R.id.btnBuyBasicDetail)
        var gmbrAuthorDetail: ImageView = view.findViewById(R.id.gmbrAuthorDetail)

        txtBalanceDetail.text = acti.userLogin!!.BALANCE.toString().toInt().toRupiah()

        Picasso.get()
            .load(env.URLIMAGE+"profile-pictures/${acti.userLogin!!.PICTURE}")
            .fit()
            .centerCrop()
            .into(gmbrAuthorDetail)
        gmbrAuthorDetail.clipToOutline = true
        gmbrAuthorDetail.setBackgroundResource(R.drawable.rounded_corner_picture_1)

        acti.coroutine.launch {
            acti.runOnUiThread{
                val client = ApiConfiguration.getApiService().getStingDetail(stingId)
                client.enqueue(object: Callback<StingDRO> {
                    override fun onResponse(call: Call<StingDRO>, response: retrofit2.Response<StingDRO>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                    var data: StingData = responseBody.data
                                    txtNamaDetail.text = data.TITLE_STING
                                    txtNamaAuthorDetail.text = data.author!!.NAMA
                                    txtRatingDetail.text = data.RATING
                                    txtDescProDetail.text = data.DESKRIPSI_PREMIUM
                                    txtHargaProDetail.text = data.PRICE_PREMIUM!!.toInt().toRupiah()
                                    txtDescBasicDetail.text = data.DESKRIPSI_BASIC
                                    txtHargaBasixDetail.text = data.PRICE_BASIC!!.toInt().toRupiah()
                                    txtJumlahReviewDetail.text = "(${data.JUMLAH_ORDER} Orders)"
                                    btnBuyProDetail.setOnClickListener {
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.frMain, BuyStingFragment(data.TITLE_STING!!,
                                                "Pro",
                                                data.DESKRIPSI_PREMIUM!!,
                                                data.PRICE_PREMIUM!!,
                                                data.author!!.NAMA.toString(),
                                                stingId
                                            ))
                                            .commit()
                                    }
                                    btnBuyBasicDetail.setOnClickListener {
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.frMain, BuyStingFragment(data.TITLE_STING!!,
                                                "Basic",
                                                data.DESKRIPSI_BASIC!!,
                                                data.PRICE_BASIC!!,
                                                data.author!!.NAMA.toString(),
                                                stingId
                                            ))
                                            .commit()
                                    }
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("GET_PROFILE_ERROR", "Fail Access: $statusCode")
                        }
                    }

                    override fun onFailure(call: Call<StingDRO>, t: Throwable) {
                        Log.e("GET_PROFILE_ERROR", "onFailure: ${t.message}")
                    }
                })
            }
        }


    }
}