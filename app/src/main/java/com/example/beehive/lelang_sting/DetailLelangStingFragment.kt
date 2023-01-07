package com.example.beehive.lelang_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.data.LelangStingData
import com.example.beehive.transaction_sting.DetailOrderedStingInProgressFragment

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
        acti.supportActionBar!!.show()
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

        }
    }
}