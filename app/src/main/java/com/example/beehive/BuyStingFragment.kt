package com.example.beehive

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.activities.MainActivity
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.Category
import com.example.beehive.data.ConversionRates
import com.example.beehive.data.ConvertionRatesDRO
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BuyStingFragment (
    var judul: String, var tipe: String, var desc: String, var harga: String, var nama: String, var stingId: String
): Fragment() {

    lateinit var txtBalanceBuy: TextView
    lateinit var txtHargaBuy: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_sting, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.show()
        acti.title = "Buy Sting"

        val txtNamaBuy: TextView = view.findViewById(R.id.txtNamaBuy)
        txtBalanceBuy = view.findViewById(R.id.txtBalanceBuy)
        val txtTipeBuy: TextView = view.findViewById(R.id.txtTipeBuy)
        val txtDescBuy: TextView = view.findViewById(R.id.txtDescBuy)
        txtHargaBuy  = view.findViewById(R.id.txtHargaBuy)
        val txtRequirementBuy: TextView = view.findViewById(R.id.txtRequirementBuy)
        val lblChangeCurrency: TextView = view.findViewById(R.id.lblChangeCurrency)
        val btnBuyBuy: Button = view.findViewById(R.id.btnBuyBuy)

        txtBalanceBuy.text = acti.userLogin!!.BALANCE!!.toBigDecimal().toInt().toRupiah()
        txtNamaBuy.text = judul
        txtTipeBuy.text = tipe
        txtDescBuy.text = desc
        txtHargaBuy.text = harga.toInt().toRupiah()

        btnBuyBuy.setOnClickListener(){
            var dateNow = Date()
            var formatter = SimpleDateFormat("dd MMMM yyyy")
            var dateString:String =  formatter.format(dateNow)

            acti.bottomSheet(judul, tipe, harga, nama, dateString, stingId, txtRequirementBuy.text.toString())
        }
        lblChangeCurrency.setOnClickListener {

            val dialogBinding = acti.layoutInflater
                .inflate(R.layout.dialog_convert_currency,null)
            val myDialog = Dialog(acti)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val btnConvert: Button = dialogBinding.findViewById(R.id.btnConvert)
            val spinnerConvert: Spinner = dialogBinding.findViewById(R.id.spinnerConvert)
            var arrayConversionRate:ArrayList<String> = arrayListOf("USD","SGD","GBP","EUR","JPY","KRW","MYR","AUD")


            var spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),android.R.layout.simple_spinner_item,arrayConversionRate
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerConvert.adapter = spinnerAdapter
            btnConvert.setOnClickListener{
                myDialog.dismiss()
                var convertTo:String = arrayConversionRate[spinnerConvert.selectedItemPosition]
                txtHargaBuy.text = "Loading..."
                val client = ApiConfiguration.getExternalApiService().fetchConversionRate()
                client.enqueue(object: Callback<ConvertionRatesDRO> {
                    override fun onResponse(call: Call<ConvertionRatesDRO>, response: retrofit2.Response<ConvertionRatesDRO>){
                        val responseBody = response.body()
                        if(response.isSuccessful){
                            if(responseBody!=null){
                                var data = responseBody.conversionRates
                                var arrRate:ArrayList<Float> = arrayListOf(
                                    data!!.USD.toString().toFloat(),
                                    data.SGD!!.toFloat(),
                                    data.GBP!!.toFloat(),
                                    data.EUR!!.toFloat(),
                                    data.JPY!!.toFloat(),
                                    data.KRW!!.toFloat(),
                                    data.MYR!!.toFloat(),
                                    data.AUD!!.toFloat(),
                                )
                                var newCurrency:Float = harga.toInt() * arrRate[spinnerConvert.selectedItemPosition]
                                txtHargaBuy.text = "$newCurrency ${arrayConversionRate[spinnerConvert.selectedItemPosition]}"

                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            acti.showModal("Semua input harus diisi!"){}

                        }
                    }

                    override fun onFailure(call: Call<ConvertionRatesDRO>, t: Throwable) {
                        acti.showModal("Poor network connection detected"){}
                        Log.e("Error convert", "onFailure: ${t.message}")
                    }
                })
            }
        }

    }
}
class rates constructor (var keyName : String, var rate : String)