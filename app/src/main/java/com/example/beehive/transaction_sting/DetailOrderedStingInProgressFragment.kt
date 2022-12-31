package com.example.beehive.transaction_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.beehive.MainActivity
import com.example.beehive.R
import com.example.beehive.lelang_sting.ListLelangStingFragment

class DetailOrderedStingInProgressFragment : Fragment() {

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
        acti.title = "Detail Ordered Sting"
        val btnDownload:Button = view.findViewById(R.id.btnDownload)
        val btnDecline:Button = view.findViewById(R.id.btnDecline)
        val btnAccept:Button = view.findViewById(R.id.btnAccept)
        val btnComplains:Button = view.findViewById(R.id.btnComplains)
        val btnBack:ImageButton = view.findViewById(R.id.btnBack)

        btnBack.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListLelangStingFragment())
                .commit()
        }
        btnDownload.setOnClickListener {

        }
        btnDecline.setOnClickListener {

        }
        btnAccept.setOnClickListener {

        }
        btnComplains.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListComplainsFragment())
                .commit()
        }
    }
}