package com.example.beehive.transaction_sting

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import com.example.beehive.activities.MainActivity
import com.example.beehive.R
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.*
import com.example.beehive.lelang_sting.ListLelangStingFragment
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback

class DetailOrderedStingInProgressFragment(
    var mode:String = "transaction", //transaction atau lelang
    var transaction:TransactionStingData? = null,
    var lelang:LelangStingData? = null,
) : Fragment() {

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
        return inflater.inflate(
            R.layout.fragment_detail_ordered_sting_in_progress,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Detail $mode Sting"
        var lblNamaSting:TextView  = view.findViewById(R.id.lblNamaSting)
        lblNamaSting.text = "Detail $mode Sting"

        val lblStatus:TextView = view.findViewById(R.id.lblStatus)
        val lblRevisionLeft:TextView = view.findViewById(R.id.lblRevisionLeft)
        val btnDownload:Button = view.findViewById(R.id.btnDownload)
        val btnDecline:Button = view.findViewById(R.id.btnDecline)
        val btnAccept:Button = view.findViewById(R.id.btnAccept)
        val btnComplains:Button = view.findViewById(R.id.btnComplains)
        val btnBack:ImageButton = view.findViewById(R.id.btnBack)

        if(mode=="transaction"){
            lblStatus.text = "Status : ${transaction!!.statusString}"
            lblRevisionLeft.text = "Revision Left : ${transaction!!.revisionLeft}"
        }else{
//            lblStatus.text = "Status : ${lelang!!.statusString}"
//            lblRevisionLeft.text = "Revision Left : ${lelang!!.revisionLeft}"
        }
        btnDecline.isVisible = false
        btnDownload.isVisible = false
        btnAccept.isVisible = false
        //todo kasih reload
        if(mode=="transaction"){
            if(transaction!!.FILENAME_FINAL!=""){
                btnDownload.isVisible = true
                if(transaction!!.revisionWaiting!!<1){
                    if(transaction!!.STATUS!!!="3"){ //kalau belum selesai
                        if(transaction!!.revisionLeft!!>0){
                            btnDecline.isVisible = true
                        }else{
                            btnDecline.isVisible = true
                            btnDecline.isEnabled = false
                        }
                        btnAccept.isVisible = true
                    }else{

                    }
                }else{
                }
            }else{
                btnDownload.isVisible = true
                btnDownload.isEnabled = false
                btnDownload.text = "There is no work delivered"
            }
        }else{
            //mode lelang TODO GANTI KE LELANG dan ganti dro
            if(lelang!!.FILENAME_FINAL!=""){
                btnDownload.isVisible = true
                if(transaction!!.revisionWaiting!!<1){
                    if(transaction!!.STATUS!!!="2"){ //kalau belum selesai
                        if(transaction!!.revisionLeft!!>0){
                            btnDecline.isVisible = true
                        }else{
                            btnDecline.isVisible = true
                            btnDecline.isEnabled = false
                        }
                        btnAccept.isVisible = true
                    }else{

                    }
                }else{
                }
            }else{
                btnDownload.isVisible = true
                btnDownload.isEnabled = false
                btnDownload.text = "There is no work delivered"
            }
        }
        btnBack.setOnClickListener{
            if(mode=="transaction"){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain, DetailOrderedStingFragment(
                        transaction!!.sting!!.author!!.NAMA!!,
                        transaction!!.REQUIREMENT_PROJECT!!,
                        transaction!!.COMMISION!!,
                        transaction!!.DATE_START!!,
                        transaction!!.DATE_END!!,
                    ))
                    .commit()
            }else{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain, ListLelangStingFragment())
                    .commit()
            }
        }
        btnDownload.setOnClickListener {
            //TODO DOWNLOAD
        }
        btnDecline.setOnClickListener {

            val dialogBinding = requireActivity().layoutInflater
                .inflate(R.layout.dialog_decline_transaction_sting,null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val btnCancel: Button = dialogBinding.findViewById(R.id.btnCancel)
            btnCancel.setOnClickListener{
                myDialog.dismiss()
            }
            val btnSubmit: Button = dialogBinding.findViewById(R.id.btnSubmit)
            val txtComplain: EditText = dialogBinding.findViewById(R.id.txtComplain)
            btnSubmit.setOnClickListener{
                //todo submit
                var complain:String = txtComplain.text.toString()
                if(complain==""){
                    Toast.makeText(requireContext(),
                        "Complain harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(mode=="transaction"){
                    val client = ApiConfiguration.getApiService().declineTransactionSting(
                        id = transaction!!.ID_TRANSACTION!!,
                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                        declineTransactionStingData = DeclineTransactionStingDTO(complain)
                    )
                    client.enqueue(object: Callback<BasicDRO> {
                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.data!=null){
                                        acti.showModal("Berhasil decline order dan " +
                                                "mengirim complain"){}
                                        //TODO RELOAD PAGE INI DAN DISABLE BUTTON
                                        myDialog.dismiss()
                                    }
                                }
                            }
                            else{
                                val statusCode:Int = response.code()
                                Log.e("ERROR DECLINE", "Fail Access: $statusCode")
                                if(statusCode==401){
                                    acti.showModal("Unauthorized"){}
                                }else if(statusCode==404){
                                    acti.showModal("Order ini tidak ditemukan!"){}
                                }else if(statusCode==403){
                                    acti.showModal("Order ini sudah mencapai batas maksimum revisi!"){}
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                            Log.e("ERROR DECLINE", "onFailure: ${t.message}")
                            acti.showModal("Poor network connection detected"){}
                        }

                    })
                }else{

                    val client = ApiConfiguration.getApiService().declineTransactionSting(
                        id = transaction!!.ID_TRANSACTION!!,
                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                        declineTransactionStingData = DeclineTransactionStingDTO(complain)
                    )
                    client.enqueue(object: Callback<BasicDRO> {
                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.data!=null){
                                        acti.showModal("Berhasil decline order dan " +
                                                "mengirim complain"){}
                                        //TODO RELOAD PAGE INI DAN DISABLE BUTTON
                                        myDialog.dismiss()
                                    }
                                }
                            }
                            else{
                                val statusCode:Int = response.code()
                                Log.e("ERROR DECLINE", "Fail Access: $statusCode")
                                if(statusCode==401){
                                    acti.showModal("Unauthorized"){}
                                }else if(statusCode==404){
                                    acti.showModal("Order ini tidak ditemukan!"){}
                                }else if(statusCode==403){
                                    acti.showModal("Order ini sudah mencapai batas maksimum revisi!"){}
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                            Log.e("ERROR DECLINE", "onFailure: ${t.message}")
                            acti.showModal("Poor network connection detected"){}
                        }

                    })
                }
            }
        }
        btnAccept.setOnClickListener {
            val dialogBinding = requireActivity().layoutInflater
                .inflate(R.layout.dialog_rate_transcation_sting,null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val ratingBar: RatingBar = dialogBinding.findViewById(R.id.ratingBar)
            var rating:Int = 0
            rating = ratingBar.rating.toInt()

            val btnCancel: Button = dialogBinding.findViewById(R.id.btnCancel)
            btnCancel.setOnClickListener{
                myDialog.dismiss()
            }
            val btnSubmit: Button = dialogBinding.findViewById(R.id.btnSubmit)
            val txtReview: EditText = dialogBinding.findViewById(R.id.txtReview)
            btnSubmit.setOnClickListener{
                //todo submit
                var review:String = txtReview.text.toString()
                if(review==""){
                    Toast.makeText(requireContext(),
                        "Review harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(mode=="transaction"){
                    val client = ApiConfiguration.getApiService().completeTransactionSting(
                        id = transaction!!.ID_TRANSACTION!!,
                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                        completeTransactionStingData = CompleteTransactionStingDTO(rating,review)
                    )
                    client.enqueue(object: Callback<BasicDRO> {
                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.data!=null){
                                        acti.showModal("Berhasil finish order! (づ￣ 3￣)づ"){}
                                        //TODO RELOAD PAGE INI DAN DISABLE BUTTON
                                        myDialog.dismiss()
                                    }
                                }
                            }
                            else{
                                val statusCode:Int = response.code()
                                Log.e("ERROR ACC", "Fail Access: $statusCode")
                                if(statusCode==401){
                                    acti.showModal("Unauthorized"){}
                                }else if(statusCode==404){
                                    acti.showModal("Order ini tidak ditemukan!"){}
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                            Log.e("ERROR ACC", "onFailure: ${t.message}")
                            acti.showModal("Poor network connection detected"){}
                        }

                    })
                }else{
                    val client = ApiConfiguration.getApiService().completeTransactionSting(
                        id = transaction!!.ID_TRANSACTION!!,
                        remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                        completeTransactionStingData = CompleteTransactionStingDTO(rating,review)
                    )
                    client.enqueue(object: Callback<BasicDRO> {
                        override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.data!=null){
                                        acti.showModal("Berhasil finish order! (づ￣ 3￣)づ"){}
                                        //TODO RELOAD PAGE INI DAN DISABLE BUTTON
                                        myDialog.dismiss()
                                    }
                                }
                            }
                            else{
                                val statusCode:Int = response.code()
                                Log.e("ERROR ACC", "Fail Access: $statusCode")
                                if(statusCode==401){
                                    acti.showModal("Unauthorized"){}
                                }else if(statusCode==404){
                                    acti.showModal("Order ini tidak ditemukan!"){}
                                }
                            }
                        }

                        override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                            Log.e("ERROR ACC", "onFailure: ${t.message}")
                            acti.showModal("Poor network connection detected"){}
                        }

                    })
                }
            }
        }
        btnComplains.setOnClickListener {
            if(mode=="transaction"){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain, ListComplainsFragment(mode,transaction,lelang,
                        transaction!!.complains!! as List<ComplainData>))
                    .commit()
            }else{
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.frMain, ListComplainsFragment(lelang!!.complains!! as List<ComplainData>))
//                    .commit()
            }
        }
    }
}