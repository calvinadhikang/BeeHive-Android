package com.example.beehive.lelang_sting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.api_config.ApiConfiguration
import com.example.beehive.data.BasicDRO
import com.example.beehive.data.LelangStingData
import com.example.beehive.data.ListLelangStingDRO
import com.example.beehive.env
import com.example.beehive.transaction_sting.DetailOrderedStingInProgressFragment
import com.example.beehive.utils.DownloadHelper
import retrofit2.Call
import retrofit2.Callback

class DetailLelangStingFragment(
    var lelang: LelangStingData
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
        return inflater.inflate(R.layout.fragment_detail_lelang_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Detail Lelang Sting"
        setHasOptionsMenu(true)

        var btnBack: ImageButton = view.findViewById(R.id.btnBack)
        var lblBeeworkerName: TextView = view.findViewById(R.id.lblBeeworkerName)
        var lblDeskripsi: TextView = view.findViewById(R.id.lblDeskripsi)
        var lblHarga: TextView = view.findViewById(R.id.lblHarga)
        var lblDateStarted: TextView = view.findViewById(R.id.lblDateStarted)
        var lblDateEnded: TextView = view.findViewById(R.id.lblDateEnded)

        var btnDetail: Button = view.findViewById(R.id.btnDetail)
        var btnCancel: Button = view.findViewById(R.id.btnCancel)
        var btnDownload: Button = view.findViewById(R.id.btnDownload3)

        lblDeskripsi.text = lelang.REQUIREMENT_PROJECT
        lblHarga.text =  lelang.COMMISION!!.toInt().toRupiah()
        if(lelang.EMAIL_BEEWORKER==null || lelang.EMAIL_BEEWORKER==""){
            lblBeeworkerName.text = "(。_。)"
            btnDownload.isEnabled = false
            btnDetail.isEnabled = false
        }else{
            lblBeeworkerName.text = lelang.EMAIL_BEEWORKER
            btnCancel.isVisible = false
        }
        if(lelang.DATE_START==null || lelang.DATE_START==""){
            lblDateStarted.text = "Not Started Yet"
        }else{
            lblDateStarted.text = lelang.DATE_START
            btnCancel.isVisible = false
        }
        if(lelang.DATE_START==null|| lelang.DATE_END==""){
            lblDateEnded.text = "Not Ended Yet"
        }else{
            lblDateEnded.text = lelang.DATE_END
        }
        if(lelang.STATUS=="-1"){
            btnCancel.isVisible = false
        }

        btnBack.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListLelangStingFragment())
                .commit()
        }
        btnDetail.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,
                    DetailOrderedStingInProgressFragment("lelang",null,lelang,)
                )
                .commit()
        }
        btnCancel.setOnClickListener {
            acti.showConfirmation("Apakah anda yakin untuk membatalkan" +
                    " lelang sting ini ?","Yes","No",{
                val client = ApiConfiguration.getApiService().cancelLelangSting(
                    id = lelang.ID_LELANG_STING!!,
                    remember_token = acti.userLogin!!.REMEMBER_TOKEN!!,
                )
                client.enqueue(object: Callback<BasicDRO> {
                    override fun onResponse(call: Call<BasicDRO>, response: retrofit2.Response<BasicDRO>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                if(responseBody.data!=null){
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frMain, ListLelangStingFragment())
                                        .commit()
                                    acti.showModal("Berhasil cancel lelang sting!"){}
                                }
                            }
                        }
                        else{
                            val statusCode:Int = response.code()
                            Log.e("ERROR FETCH", "Fail Access: $statusCode")
                            if(statusCode==401){
                                acti.showModal("Unauthorized"){}
                            } else if(statusCode==403){
                                acti.showModal("Lelang ini tidak dalam keadaan pending"){}
                            }else if(statusCode==404){
                                acti.showModal("Lelang Sting tidak ditemukan"){}
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicDRO>, t: Throwable) {
                        Log.e("ERROR cancel", "onFailure: ${t.message}")
                        acti.showModal("Poor network connection detected"){}
                    }

                })
            },{})
        }
        btnDownload.setOnClickListener {
            var namafile:String = ""
            var extension:String = ""
            var outputName:String = ""
            namafile = lelang!!.FILENAME_FINAL.toString()
            if(namafile==""){
                acti.showModal("Belum ada submission!"){}
                return@setOnClickListener
            }
            outputName = "DownloadResult${lelang?.ID_LELANG_STING}"
            try {
                extension = namafile.split('.')[1]
            }catch (e:Error){
                extension = "jpeg"
            }
            DownloadHelper.download(requireActivity(),
                env.URLIMAGE+"order-deliver/"+namafile, namafile,
                outputName,extension)
        }
    }
}