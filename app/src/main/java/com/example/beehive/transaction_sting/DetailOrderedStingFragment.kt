package com.example.beehive.transaction_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.data.TransactionStingData

class DetailOrderedStingFragment (
   var namaBeeworker: String, var requirement: String, var harga: String, var tglMulai: String, var tglSelesai: String, var trans: TransactionStingData
): Fragment() {

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
        return inflater.inflate(R.layout.fragment_detail_ordered_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Detail Ordered Sting"
        var btnDetail:Button = view.findViewById(R.id.btnDetail)
        var lblNamaSting: TextView = view.findViewById(R.id.lblNamaSting)
        var lblBeeworkerName: TextView = view.findViewById(R.id.lblBeeworkerName)
        var lblDeskripsi: TextView = view.findViewById(R.id.lblDeskripsi)
        var lvlharga: TextView = view.findViewById(R.id.lblHarga)
        var lblDateStarted: TextView = view.findViewById(R.id.lblDateStarted)
        var lblDateEnded: TextView = view.findViewById(R.id.lblDateEnded)
        var btnDownload: Button = view.findViewById(R.id.btnDownload3)
        var btnBackn: ImageView = view.findViewById(R.id.btnBackRegisterFinal)

        lblNamaSting.text = trans.sting!!.TITLE_STING
        lblBeeworkerName.text = namaBeeworker
        lblDeskripsi.text = trans.sting!!.DESKRIPSI
        lvlharga.text = "Rp.$harga"
        lblDateStarted.text = tglMulai
        lblDateEnded.text = tglSelesai

        btnBackn.setOnClickListener{
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.frMain, NotificationFragment())
//                .commit()
            activity?.supportFragmentManager?.popBackStack();
        }


        btnDetail.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,
                    DetailOrderedStingInProgressFragment("transaction",null,null,))
                .commit()
        }
    }
}