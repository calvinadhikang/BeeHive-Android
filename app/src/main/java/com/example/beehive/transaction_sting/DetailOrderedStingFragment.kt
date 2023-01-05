package com.example.beehive.transaction_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.data.ComplainData
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
        btnDetail.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,
                    DetailOrderedStingInProgressFragment("transaction",null,null,))
                .commit()
        }
    }
}