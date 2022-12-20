package com.example.beehive.topup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.TopUpDTO
import com.example.beehive.user_auth.RegisterFinalFragment
import com.example.beehive.user_profile.UserProfileFragment
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class TopUpFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_top_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        val btnBack: ImageButton = view.findViewById(R.id.btnBack)
        val btn10k: Button = view.findViewById(R.id.btn10k)
        val btn20k: Button = view.findViewById(R.id.btn20k)
        val btn50k: Button = view.findViewById(R.id.btn50k)
        val btn100k: Button = view.findViewById(R.id.btn100k)
        val btn1jt: Button = view.findViewById(R.id.btn1jt)
        val btn500k: Button = view.findViewById(R.id.btn500k)
        val btnTopUp: Button = view.findViewById(R.id.btnTopUp)
        val txtNominal: EditText = view.findViewById(R.id.txtNominal)

        btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, UserProfileFragment())
                .commit()
        }
        btnTopUp.setOnClickListener {
            var nominalString:String = txtNominal.text.toString()
            if(nominalString==""){
                acti.showModal("Jumlah nominal top up harus diisi"){}
                return@setOnClickListener
            }
            var nominal:Int = nominalString.toInt()
            if(nominal<10000){
                acti.showModal("Jumlah minimal nominal top up adalah Rp10.000"){}
                return@setOnClickListener
            }
            val dialogBinding = requireActivity().layoutInflater
                .inflate(R.layout.dialog_pay_topup,null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            var fee:Int = 1000

            var dateNow = Date()
            var formatter = SimpleDateFormat("dd L yyyy")
            var dateString:String =  formatter.format(dateNow)

            var total:Int = nominal + fee
            val lblName: TextView = dialogBinding.findViewById(R.id.lblNameDetailBilling)
            val lblDate: TextView = dialogBinding.findViewById(R.id.lblDate)
            val lblNominal: TextView = dialogBinding.findViewById(R.id.lblNominal)
            val lblFee: TextView = dialogBinding.findViewById(R.id.lblFee)
            val lblTotal: TextView = dialogBinding.findViewById(R.id.lblNominalTotal)

            lblName.text = acti.userLogin!!.NAMA
            lblFee.text = fee.toRupiah()
            lblDate.text = dateString
            lblNominal.text = nominal.toRupiah()
            lblTotal.text = total.toRupiah()

            val btnPay: Button = dialogBinding.findViewById(R.id.btnPay)
            val btnCancel: Button = dialogBinding.findViewById(R.id.btnCancel)
            btnPay.setOnClickListener{
                myDialog.dismiss()
                val client = ApiConfiguration.getApiService().TopUp(
                    remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                    topUpData = TopUpDTO(nominal)
                )
                client.enqueue(object: Callback<BasicDRO> {
                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                        if(response.isSuccessful){
                            acti.showModal("Berhasil top up!"){}
                            acti.reloadUser()
                            txtNominal.setText("")
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("ERROR TOPUP", "Fail Access: $statusCode")
                            if(statusCode==401){
                                acti.showModal("Unauthorized"){}
                            }else if(statusCode==400){
                                acti.showModal("Nominal tidak ada"){}
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                        Log.e("ERROR TOPUP", "onFailure: ${t.message}")
                        acti.showModal("Poor network connection detected"){}
                    }

                })

            }
            btnCancel.setOnClickListener {
                myDialog.dismiss()
            }
        }
        btn10k.setOnClickListener {
            txtNominal.setText("10000")
        }
        btn20k.setOnClickListener {
            txtNominal.setText("20000")
        }
        btn50k.setOnClickListener {
            txtNominal.setText("50000")
        }
        btn100k.setOnClickListener {
            txtNominal.setText("100000")
        }
        btn500k.setOnClickListener {
            txtNominal.setText("500000")
        }
        btn1jt.setOnClickListener {
            txtNominal.setText("1000000")
        }
    }
}